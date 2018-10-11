//package ua.levelup.dao.impl;
//
//import ua.levelup.dao.OrderDao;
//import ua.levelup.dao.support.SqlContainer;
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.support.MessageContainer;
//import ua.levelup.mapper.support.MapperHolder;
//import ua.levelup.model.Order;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.sql.*;
//import java.util.List;
//
//public class OrderDaoImpl extends AbstractDaoImpl implements OrderDao {
//
//    private static final Logger logger = LogManager.getLogger();
//
//    public OrderDaoImpl() {
//        super();
//    }
//
//    public OrderDaoImpl(Connection connection) {
//        super(connection);
//    }
//
//    private final static String INSERT_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("INSERT_ORDER");
//
//    @Override
//    public Order add(Order order) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[]{"id"})) {
//            statement.setInt(1, order.getUser().getId());
//            statement.setInt(2, order.get().ordinal());
//            statement.setTimestamp(3, order.getDate());
//            statement.setString(4, order.getAddress());
//            statement.setInt(5, order.get().ordinal());
//            statement.executeUpdate();
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                order.setId(generatedKeys.getInt(1));
//            }
//            return order;
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_INSERT_ORDER"), e);
//        }
//    }
//
//    private final static String UPDATE_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("UPDATE_ORDER");
//
//    @Override
//    public int update(Order order) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
//            statement.setInt(1, order.getUser().getId());
//            statement.setInt(2, order.get().ordinal());
//            statement.setTimestamp(3, order.getDate());
//            statement.setString(4, order.getAddress());
//            statement.setInt(5, order.get().ordinal());
//            statement.setInt(6, order.getId());
//            return statement.executeUpdate();
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_UPDATE_ORDER"), e);
//        }
//    }
//
//    private final static String DELETE_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("DELETE_ORDER");
//
//    @Override
//    public int delete(int orderId) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
//            statement.setInt(1, orderId);
//            return statement.executeUpdate();
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_DELETE_ORDER"), e);
//        }
//    }
//
//    private final static String SELECT_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("SELECT_ORDER");
//
//    @Override
//    public Order getById(int orderId) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY)) {
//            statement.setInt(1, orderId);
//            ResultSet resultSet = statement.executeQuery();
//            return (Order) MapperHolder.getMapper(MapperHolder.ORDER).mapRow(resultSet);
//        } catch (ApplicationException e) {
//            logger.error(e.getMessage());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("EMPTY_RESULTSET") + Order.class, e);
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_GET_ORDER"), e);
//        }
//    }
//
//    private final static String SELECT_BY_USER_ID_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("SELECT_ORDERS_BY_USER_ID");
//
//    @Override
//    public List<Order> getAllByUserId(int userId) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID_QUERY)) {
//            statement.setInt(1, userId);
//            ResultSet resultSet = statement.executeQuery();
//            return (List<Order>) MapperHolder.getMapper(MapperHolder.ORDER).mapAllRows(resultSet);
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_GET_FILTERED_ORDERS"), e);
//        }
//    }
//
//    private final static String SELECT_ALL_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("SELECT_ALL_ORDERS");
//
//    @Override
//    public List<Order> getAll() throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
//            ResultSet resultSet = statement.executeQuery();
//            return (List<Order>) MapperHolder.getMapper(MapperHolder.ORDER)
//                    .mapAllRows(resultSet);
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_GET_MANUFACTURERS"), e);
//        }
//    }
//}
