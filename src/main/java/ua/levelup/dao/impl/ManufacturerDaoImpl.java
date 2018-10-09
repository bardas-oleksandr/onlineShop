//package ua.levelup.dao.impl;
//
//import ua.levelup.dao.ManufacturerDao;
//import ua.levelup.dao.support.SqlContainer;
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.support.MessageContainer;
//import ua.levelup.mapper.support.MapperHolder;
//import ua.levelup.model.Manufacturer;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.sql.*;
//import java.util.List;
//
//public class ManufacturerDaoImpl extends AbstractDaoImpl implements ManufacturerDao {
//
//    private static final Logger logger = LogManager.getLogger();
//
//    public ManufacturerDaoImpl() {
//        super();
//    }
//
//    public ManufacturerDaoImpl(Connection connection) {
//        super(connection);
//    }
//
//    private final static String INSERT_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("INSERT_MANUFACTURER");
//
//    @Override
//    public Manufacturer add(Manufacturer manufacturer) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[]{"id"})) {
//            statement.setString(1, manufacturer.getName());
//            statement.executeUpdate();
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                manufacturer.setId(generatedKeys.getInt(1));
//            }
//            return manufacturer;
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            Throwable cause = e.getCause();
//            if (cause != null && cause.getMessage().startsWith("Duplicate entry")) {
//                throw new ApplicationException(MessageContainer.getMessageProperties()
//                        .getProperty("NOT_UNIQUE_MANUFACTURER"), e);
//            }
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_INSERT_MANUFACTURER"), e);
//        }
//    }
//
//    private final static String UPDATE_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("UPDATE_MANUFACTURER");
//
//    @Override
//    public int update(Manufacturer manufacturer) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
//            statement.setString(1, manufacturer.getName());
//            statement.setInt(2, manufacturer.getId());
//            return statement.executeUpdate();
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_UPDATE_MANUFACTURER"), e);
//        }
//    }
//
//    private final static String DELETE_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("DELETE_MANUFACTURER");
//
//    @Override
//    public int delete(int id) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
//            statement.setInt(1, id);
//            return statement.executeUpdate();
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_DELETE_MANUFACTURER"), e);
//        }
//    }
//
//    private final static String SELECT_BY_ID_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("SELECT_MANUFACTURER_BY_ID");
//
//    @Override
//    public Manufacturer getById(int id) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            return (Manufacturer) MapperHolder.getMapper(MapperHolder.MANUFACTURER)
//                    .mapRow(resultSet);
//        } catch (ApplicationException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("EMPTY_RESULTSET") + Manufacturer.class, e);
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_GET_MANUFACTURER"), e);
//        }
//    }
//
//    private final static String SELECT_BY_CATEGORY_ID_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("SELECT_MANUFACTURERS_BY_CATEGORY_ID");
//
//    @Override
//    public List<Manufacturer> getAllByCategoryId(int categoryId) throws ApplicationException {
//        try (PreparedStatement statement = connection
//                .prepareStatement(SELECT_BY_CATEGORY_ID_QUERY)) {
//            statement.setInt(1, categoryId);
//            ResultSet resultSet = statement.executeQuery();
//            return (List<Manufacturer>) MapperHolder.getMapper(MapperHolder.MANUFACTURER)
//                    .mapAllRows(resultSet);
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_GET_MANUFACTURERS"), e);
//        }
//    }
//
//    private final static String SELECT_ALL_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("SELECT_ALL_MANUFACTURERS");
//
//    @Override
//    public List<Manufacturer> getAll() throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
//            ResultSet resultSet = statement.executeQuery();
//            return (List<Manufacturer>) MapperHolder.getMapper(MapperHolder.MANUFACTURER)
//                    .mapAllRows(resultSet);
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_GET_MANUFACTURERS"), e);
//        }
//    }
//}
