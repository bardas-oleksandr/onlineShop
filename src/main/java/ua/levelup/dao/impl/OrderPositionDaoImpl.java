package ua.levelup.dao.impl;

import ua.levelup.dao.OrderPositionDao;
import ua.levelup.dao.support.SqlHolder;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.mapper.support.MapperHolder;
import ua.levelup.model.OrderPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class OrderPositionDaoImpl extends AbstractDaoImpl implements OrderPositionDao {

    private static final Logger logger = LogManager.getLogger();

    public OrderPositionDaoImpl(){super();}

    public OrderPositionDaoImpl(Connection connection){super(connection);}

    private final static String INSERT_QUERY = SqlHolder.getSqlProperties()
            .getProperty("INSERT_ORDER_POSITION");
    @Override
    public void addAll(List<OrderPosition> orderPositionList) throws ApplicationException {
            try{
            boolean autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[]{"id"})) {
                for (OrderPosition orderPosition: orderPositionList) {
                    statement.setInt(1,orderPosition.getOrderId());
                    statement.setInt(2, orderPosition.getProductId());
                    statement.setInt(3, orderPosition.getQuantity());
                    statement.setFloat(4,orderPosition.getUnitPrice());
                    statement.addBatch();
                }
                statement.executeBatch();
                connection.commit();
                connection.setAutoCommit(autoCommit);
            }catch(SQLException e){
                connection.rollback();
                logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
                throw new ApplicationException(MessageHolder.getMessageProperties()
                        .getProperty("FAILED_INSERT_ORDER_POSITION_LIST"), e);
            }
        } catch(ApplicationException | SQLException e){
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_INSERT_ORDER_POSITION_LIST"), e);
        }
    }

    private final static String UPDATE_QUERY = SqlHolder.getSqlProperties()
            .getProperty("UPDATE_ORDER_POSITION");
    @Override
    public int update(int orderId, int productId, OrderPosition orderPosition) throws ApplicationException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, orderId);
            statement.setInt(2, orderPosition.getProductId());
            statement.setInt(3, orderPosition.getQuantity());
            statement.setFloat(4, orderPosition.getUnitPrice());
            statement.setInt(5, orderId);
            statement.setInt(6, productId);
            return statement.executeUpdate();
        }catch(SQLException e){
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_UPDATE_ORDER_POSITION"), e);
        }
    }

    private final static String DELETE_QUERY = SqlHolder.getSqlProperties()
            .getProperty("DELETE_ORDER_POSITION");
    @Override
    public int delete(int orderId, int productId) throws ApplicationException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, orderId);
            statement.setInt(2, productId);
            return statement.executeUpdate();
        }catch(SQLException e){
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_DELETE_ORDER_POSITION"), e);
        }
    }

    private final static String SELECT_QUERY = SqlHolder.getSqlProperties()
            .getProperty("SELECT_ORDER_POSITION");
    @Override
    public OrderPosition getByPrimaryKey(int orderId, int productId) throws ApplicationException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY)) {
            statement.setInt(1, orderId);
            statement.setInt(2, productId);
            ResultSet resultSet = statement.executeQuery();
            return (OrderPosition) MapperHolder.getMapper(MapperHolder.ORDER_POSITION).mapRow(resultSet);
        }catch(ApplicationException e){
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("EMPTY_RESULTSET") + OrderPosition.class, e);
        } catch(SQLException e){
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_GET_ORDER_POSITION"), e);
        }
    }

    private final static String DELETE_BY_ORDER_ID_QUERY = SqlHolder.getSqlProperties()
            .getProperty("DELETE_ORDER_POSITIONS_BY_ORDER_ID");
    @Override
    public int deleteAllByOrderId(int orderId) throws ApplicationException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_ORDER_ID_QUERY)) {
            statement.setInt(1, orderId);
            return statement.executeUpdate();
        }catch(SQLException e){
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_DELETE_ALL_ORDER_POSITIONS"), e);
        }
    }

    private final static String SELECT_BY_ORDER_ID_QUERY = SqlHolder.getSqlProperties()
            .getProperty("SELECT_ORDER_POSITIONS_BY_ORDER_ID");
    @Override
    public List<OrderPosition> getAllByOrderId(int orderId) throws ApplicationException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ORDER_ID_QUERY)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            return (List<OrderPosition>) MapperHolder.getMapper(MapperHolder.ORDER_POSITION).mapAllRows(resultSet);
        }catch(SQLException e){
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_GET_ALL_ORDER_POSITIONS"), e);
        }
    }
}
