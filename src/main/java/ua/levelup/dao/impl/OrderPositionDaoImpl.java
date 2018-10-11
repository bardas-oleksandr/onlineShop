package ua.levelup.dao.impl;

import lombok.NonNull;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.object.BatchSqlUpdate;
import ua.levelup.dao.OrderPositionDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.logger.ShopLogger;
import ua.levelup.model.OrderPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPositionDaoImpl extends AbstractDaoImpl implements OrderPositionDao, ShopLogger {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void addAll(@NonNull List<OrderPosition> orderPositionList) throws ApplicationException {
        String sql = "INSERT INTO orders_products " +
                "(order_id, product_id, order_product_quantity, order_product_unit_price) " +
                "VALUES (:order_id,:product_id,:order_product_quantity,:order_product_unit_price)";

        DataSource dataSource = namedParameterJdbcTemplate.getJdbcTemplate().getDataSource();
        BatchSqlUpdate batchSqlUpdate = new InsertOrderPositionBatchSqlUpdate(dataSource);

        try {
            for (OrderPosition orderPosition : orderPositionList) {
                Map<String, Object> map = new HashMap<>();
                map.put("order_id", orderPosition.getOrderId());
                map.put("product_id", orderPosition.getProductId());
                map.put("order_product_quantity", orderPosition.getQuantity());
                map.put("order_product_unit_price", orderPosition.getUnitPrice());
                batchSqlUpdate.updateByNamedParam(map);
            }
            batchSqlUpdate.flush();
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("NOT_UNIQUE_ORDER_POSITION"), e);
        } catch (DataIntegrityViolationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER_POSITION"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_SAVE_ORDER_POSITION"), e);
        }
    }

    @Override
    public void update(@NonNull OrderPosition orderPosition) throws ApplicationException {
        String sql = "UPDATE orders_products " +
                "SET order_product_quantity=:order_product_quantity, " +
                "order_product_unit_price=:order_product_unit_price " +
                "WHERE order_id=:order_id " +
                "AND product_id=:product_id";

        MapSqlParameterSource source = getSqlParameterSource(orderPosition);
        saveOrUpdate(sql, source);
    }

    @Override
    public void delete(int orderId, int productId) throws ApplicationException {
        String sql = "DELETE FROM orders_products WHERE order_id=:order_id AND product_id=:product_id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("order_id", orderId);
        source.addValue("product_id", productId);

        try {
            if (namedParameterJdbcTemplate.update(sql, source) == 0) {
                throw new ApplicationException(messagesProperties
                        .getProperty("FAILED_DELETE_ORDER_POSITION_NONEXISTENT"));
            }
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_DELETE_ORDER_POSITION"), e);
        }
    }

    @Override
    public OrderPosition getByPrimaryKey(int orderId, int productId) throws ApplicationException {
        String sql = "SELECT orders_products.product_id, orders_products.order_product_quantity, " +
                "orders_products.order_product_unit_price, products.product_name " +
                "FROM orders_products, products " +
                "WHERE orders_products.product_id = products.id " +
                "AND orders_products.order_id=:order_id " +
                "AND orders_products.product_id=:product_id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("order_id", orderId);
        source.addValue("product_id", productId);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, source
                    , new OrderPositionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("EMPTY_RESULTSET")
                    + OrderPosition.class, e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_GET_ORDER_POSITION"), e);
        }
    }

    @Override
    public int deleteAllByOrderId(int orderId) throws ApplicationException {
        String sql = "DELETE FROM orders_products WHERE order_id=:order_id";

        try {
            return namedParameterJdbcTemplate.update(sql,
                    new MapSqlParameterSource("order_id", orderId));
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_DELETE_ORDER_POSITIONS_FOR_ORDER"), e);
        }
    }

    @Override
    public List<OrderPosition> getAllByOrderId(int orderId) throws ApplicationException {
        String sql = "SELECT orders_products.product_id," +
                " orders_products.order_product_quantity, orders_products.order_product_unit_price," +
                " products.product_name " +
                "FROM orders_products, products " +
                "WHERE orders_products.product_id = products.id " +
                "AND order_id=:order_id" +
                "ORDER BY order_product_unit_price";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("order_id", orderId);

        try {
            return namedParameterJdbcTemplate.query(sql, source
                    , new OrderPositionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("EMPTY_RESULTSET")
                    + OrderPosition.class, e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_GET_ORDER_POSITIONS_FOR_ORDER"), e);
        }
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    private MapSqlParameterSource getSqlParameterSource(OrderPosition orderPosition) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("order_id", orderPosition.getOrderId());
        source.addValue("product_id", orderPosition.getProductId());
        source.addValue("order_product_quantity", orderPosition.getQuantity());
        source.addValue("order_product_unit_price", orderPosition.getUnitPrice());
        return source;
    }

    private void saveOrUpdate(String sql, MapSqlParameterSource source) {
        try {
            namedParameterJdbcTemplate.update(sql, source);
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("NOT_UNIQUE_ORDER_POSITION"), e);
        } catch (DataIntegrityViolationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER_POSITION"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_SAVE_ORDER_POSITION"), e);
        }
    }

    /**
     *
     */
    private class OrderPositionRowMapper implements RowMapper<OrderPosition> {

        @Override
        public OrderPosition mapRow(ResultSet resultSet, int i) throws SQLException {
            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setOrderId(resultSet.getInt("order_id"));
            orderPosition.setProductId(resultSet.getInt("product_id"));
            orderPosition.setQuantity(resultSet.getInt("order_product_quantity"));
            orderPosition.setUnitPrice(resultSet.getFloat("order_product_unit_price"));
            orderPosition.setProductName(resultSet.getString("product_name"));
            return orderPosition;
        }
    }

    /**
     *
     */
    private class InsertOrderPositionBatchSqlUpdate extends BatchSqlUpdate {
        private static final String SQL = "INSERT INTO orders_products " +
                "(order_id, product_id, order_product_quantity, order_product_unit_price) " +
                "VALUES (:order_id,:product_id,:order_product_quantity,:order_product_unit_price)";

        private static final int BATCH_SIZE = 10;

        public InsertOrderPositionBatchSqlUpdate(DataSource dataSource) {
            super(dataSource, SQL);
            declareParameter(new SqlInOutParameter("order_id", Types.INTEGER));
            declareParameter(new SqlInOutParameter("product_id", Types.INTEGER));
            declareParameter(new SqlInOutParameter("order_product_quantity", Types.INTEGER));
            declareParameter(new SqlInOutParameter("order_product_unit_price", Types.FLOAT));
            setBatchSize(BATCH_SIZE);
        }
    }
}