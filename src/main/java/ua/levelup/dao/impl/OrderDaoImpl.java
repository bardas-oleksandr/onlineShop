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
import org.springframework.stereotype.Repository;
import ua.levelup.dao.OrderDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.logger.ShopLogger;
import ua.levelup.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.levelup.model.OrderPosition;
import ua.levelup.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Repository("orderDao")
public class OrderDaoImpl extends AbstractDaoImpl implements OrderDao, ShopLogger {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(@NonNull Order order) throws ApplicationException {
        String sql = "INSERT INTO orders (order_user_id, order_address, order_date, order_payed" +
                ", order_state, order_payment_conditions) " +
                "VALUES (:order_user_id,:order_address,:order_date,:order_payed" +
                ",:order_state,:order_payment_conditions)";

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("order_user_id", order.getUser().getId());
            source.addValue("order_address", order.getAddress());
            source.addValue("order_date", order.getDate());
            source.addValue("order_payed", order.isPayed());
            source.addValue("order_state", order.getOrderState().ordinal());
            source.addValue("order_payment_conditions", order
                    .getPaymentConditions().ordinal());

            namedParameterJdbcTemplate.update(sql, source, keyHolder);
            order.setId(keyHolder.getKey().intValue());
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("NOT_UNIQUE_ORDER"), e);
        } catch (DataIntegrityViolationException | NullPointerException e) {
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
                ", order_date=:order_date, order_payed=:order_payed, order_state=:order_state" +
                ", order_payment_conditions=:order_payment_conditions " +
                "WHERE id=:id";

        try {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("order_user_id", order.getUser().getId());
            source.addValue("order_address", order.getAddress());
            source.addValue("order_date", order.getDate());
            source.addValue("order_payed", order.isPayed());
            source.addValue("order_state", order.getOrderState().ordinal());
            source.addValue("order_payment_conditions", order.getPaymentConditions().ordinal());
            source.addValue("id", order.getId());

            updateOrDelete(sql,source);
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("NOT_UNIQUE_ORDER"), e);
        } catch (DataIntegrityViolationException | NullPointerException e) {
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

        MapSqlParameterSource source = new MapSqlParameterSource("id", orderId);

        try {
            updateOrDelete(sql,source);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_DELETE_ORDER"), e);
        }
    }

    @Override
    public Order getById(int orderId) throws ApplicationException {
        String sql = "SELECT orders.id, orders.order_user_id, orders.order_address" +
                ", orders.order_date, orders.order_payed, orders.order_state, orders.order_payment_conditions" +
                ", users.user_name, users.user_email, users.user_state" +
                ", orders_products.product_id, orders_products.order_product_quantity" +
                ", orders_products.order_product_unit_price, products.product_name " +
                "FROM orders, users " +
                "LEFT JOIN orders_products " +
                "ON orders_products.order_id=:id " +
                "LEFT JOIN products " +
                "ON products.id=orders_products.product_id " +
                "WHERE orders.order_user_id=users.id " +
                "AND orders.id=:id";

        try {
            List<Order> result =  namedParameterJdbcTemplate.query(sql
                    , new MapSqlParameterSource("id", orderId)
                    , new OrderResultSetExtractor());
            if(result.size() == 0){
                String message = messagesProperties.getProperty("EMPTY_RESULTSET") + Order.class;
                logErrorMessage(message);
                throw new ApplicationException(message);
            }
            return result.get(0);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("FAILED_GET_ORDER"), e);
        }
    }

    @Override
    public List<Order> getAllByUserId(int userId) throws ApplicationException {
        String sql = "SELECT orders.id, orders.order_user_id, orders.order_address" +
                ", orders.order_date, orders.order_payed, orders.order_state" +
                ", orders.order_payment_conditions, users.user_name" +
                ", users.user_email, users.user_state " +
                ", orders_products.product_id, orders_products.order_product_quantity" +
                ", orders_products.order_product_unit_price, products.product_name " +
                "FROM orders " +
                "INNER JOIN users ON orders.order_user_id = users.id " +
                "AND users.id=:user_id " +
                "LEFT JOIN orders_products ON orders.id = orders_products.order_id " +
                "LEFT JOIN products ON orders_products.product_id = products.id " +
                "ORDER BY orders.order_date";

        try {
            return namedParameterJdbcTemplate.query(sql
                    , new MapSqlParameterSource("user_id", userId)
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
                ", orders.order_date, orders.order_payed, orders.order_state" +
                ", orders.order_payment_conditions, users.user_name" +
                ", users.user_email, users.user_state " +
                ", orders_products.product_id, orders_products.order_product_quantity" +
                ", orders_products.order_product_unit_price, products.product_name " +
                "FROM orders " +
                "INNER JOIN users ON orders.order_user_id = users.id " +
                "LEFT JOIN orders_products ON orders.id = orders_products.order_id " +
                "LEFT JOIN products ON orders_products.product_id = products.id " +
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

    private void updateOrDelete(String sql, MapSqlParameterSource source){
        if(namedParameterJdbcTemplate.update(sql, source) == 0){
            String message = messagesProperties
                    .getProperty("FAILED_UPDATE_ORDER_NONEXISTENT");
            logErrorMessage(message);
            throw new ApplicationException(message);
        }
    }

    /**
     *
     */
    private class OrderResultSetExtractor implements ResultSetExtractor<List<Order>> {

        @Override
        public List<Order> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Integer, Order> map = new HashMap<>();
            Order order;
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                if ((order = map.get(id)) == null) {
                    order = new Order();
                    order.setId(id);
                    order.setAddress(resultSet.getString("order_address"));
                    order.setDate(resultSet.getTimestamp("order_date"));
                    order.setPayed(resultSet.getBoolean("order_payed"));
                    order.setOrderState(Order.OrderState.get(resultSet.getInt("order_state")));
                    order.setPaymentConditions(Order.PaymentConditions
                            .get(resultSet.getInt("order_payment_conditions")));

                    User user = new User();
                    user.setId(resultSet.getInt("order_user_id"));
                    user.setUserName(resultSet.getString("user_name"));
                    user.setEmail(resultSet.getString("user_email"));
                    user.setUserState(User.UserState.get(resultSet.getInt("user_state")));
                    order.setUser(user);

                    map.put(id, order);
                }
                if(resultSet.getInt("product_id") != 0){
                    order.addOrderPosition(extractOrderPosition(resultSet));
                }
            }
            if(map.size() == 0){
                return new ArrayList<>();
            }
            return new ArrayList<>(map.values());
        }

        private OrderPosition extractOrderPosition(ResultSet resultSet) throws SQLException {
            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setOrderId(resultSet.getInt("id"));
            orderPosition.setProductId(resultSet.getInt("product_id"));
            orderPosition.setProductName(resultSet.getString("product_name"));
            orderPosition.setQuantity(resultSet.getInt("order_product_quantity"));
            orderPosition.setUnitPrice(resultSet.getFloat("order_product_unit_price"));
            return orderPosition;
        }
    }
}