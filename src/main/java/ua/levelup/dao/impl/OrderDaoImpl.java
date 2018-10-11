package ua.levelup.dao.impl;

import lombok.NonNull;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.levelup.dao.OrderDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.logger.ShopLogger;
import ua.levelup.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class OrderDaoImpl extends AbstractDaoImpl implements OrderDao, ShopLogger {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(@NonNull Order order) throws ApplicationException {
        String sql = "INSERT INTO orders (order_user_id, order_address, order_date, order_payed" +
                ", order_state, order_payment_conditions) " +
                "VALUES (:order_user_id,:order_address,:order_date,:order_payed" +
                ",:order_state,:order_payment_conditions)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("order_user_id", order.getUser().getId());
        source.addValue("order_address", order.getAddress());
        source.addValue("order_date", order.getDate());
        source.addValue("order_payed", order.isPayed());
        source.addValue("order_state", order.getOrderState().ordinal());
        source.addValue("order_payment_conditions", order.getPaymentConditions().ordinal());

        try {
            namedParameterJdbcTemplate.update(sql, source, keyHolder);
            order.setId(keyHolder.getKey().intValue());
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("NOT_UNIQUE_ORDER"), e);
        } catch (DataIntegrityViolationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_SAVE_ORDER"), e);
        }
    }

    @Override
    public void update(@NonNull Order order) throws ApplicationException {
        String sql = "UPDATE orders SET order_user_id=:order_user_id, order_address=:order_address" +
                ", order_date=:order_date, order_payed:order_payed, order_state=:order_state" +
                ", order_payment_conditions=:order_payment_conditions " +
                "WHERE id=:id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("order_user_id", order.getUser().getId());
        source.addValue("order_address", order.getAddress());
        source.addValue("order_date", order.getDate());
        source.addValue("order_payed", order.isPayed());
        source.addValue("order_state", order.getOrderState().ordinal());
        source.addValue("order_payment_conditions", order.getPaymentConditions().ordinal());
        source.addValue("id", order.getId());

        try {
            namedParameterJdbcTemplate.update(sql, source);
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("NOT_UNIQUE_ORDER"), e);
        } catch (DataIntegrityViolationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_SAVE_ORDER"), e);
        }
    }

    @Override
    public void delete(int orderId) throws ApplicationException {
        String sql = "DELETE FROM orders WHERE id=:id";

        try {
            if (namedParameterJdbcTemplate.update(sql
                    , new MapSqlParameterSource("id", orderId)) == 0) {
                throw new ApplicationException(messagesProperties
                        .getProperty("FAILED_DELETE_ORDER_NONEXISTENT"));
            }
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_DELETE_ORDER"), e);
        }
    }

    @Override
    public Order getById(int orderId) throws ApplicationException {
        String sql = "SELECT orders.id, orders.order_user_id, orders.order_address" +
                ", orders.order_date, order.order_payed, orders.order_state" +
                ", orders.order_payment_conditions, users.id, users.user_name" +
                ", users.user_email, users.user_state " +
                "FROM orders, users " +
                "LEFT JOIN orders_products ON orders.id=orders_products.order_id " +
                "WHERE orders.order_user_id = users.id " +
                "AND orders.id=:id";

        try {
            return namedParameterJdbcTemplate.query(sql
                    , new MapSqlParameterSource("id", orderId)
                    , new OrderResultSetExtractor()).get(0);
        } catch (EmptyResultDataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("EMPTY_RESULTSET")
                    + Order.class, e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("FAILED_GET_ORDER"), e);
        }
    }

    @Override
    public List<Order> getAllByUserId(int userId) throws ApplicationException {
        String sql = "SELECT orders.id, orders.order_user_id, orders.order_address" +
                ", orders.order_date, order.order_payed, orders.order_state" +
                ", orders.order_payment_conditions, users.id, users.user_name" +
                ", users.user_email, users.user_state " +
                "FROM orders, users " +
                "LEFT JOIN orders_products ON orders.id=orders_products.order_id " +
                "WHERE orders.order_user_id = users.id " +
                "AND orders.order_user_id=:order_user_id " +
                "ORDER BY orders.order_date";

        try {
            return namedParameterJdbcTemplate.query(sql
                    , new MapSqlParameterSource("order_user_id", userId)
                    , new OrderResultSetExtractor());
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_GET_FILTERED_ORDERS"), e);
        }
    }

    @Override
    public List<Order> getAll() throws ApplicationException {
        String sql = "SELECT orders.id, orders.order_user_id, orders.order_address" +
                ", orders.order_date, order.order_payed, orders.order_state" +
                ", orders.order_payment_conditions, users.id, users.user_name" +
                ", users.user_email, users.user_state " +
                "FROM orders, users " +
                "LEFT JOIN orders_products ON orders.id=orders_products.order_id " +
                "WHERE orders.order_user_id = users.id " +
                "ORDER BY orders.order_date";

        try {
            return namedParameterJdbcTemplate.query(sql
                    , new OrderResultSetExtractor());
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_GET_MANUFACTURERS"), e);
        }
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    /**
     *
     */
    private class OrderResultSetExtractor implements ResultSetExtractor<List<Order>> {

        @Override
        public List<Order> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            return null;
        }
    }
}