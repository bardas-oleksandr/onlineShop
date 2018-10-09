//package ua.levelup.dao.impl;
//
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import ua.levelup.dao.CategoryDao;
//import ua.levelup.dao.support.SqlContainer;
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.support.MessageContainer;
//import ua.levelup.mapper.support.MapperHolder;
//import ua.levelup.model.Category;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.sql.*;
//import java.util.List;
//
//@Setter
//public class CategoryDaoImpl extends AbstractDaoImpl implements CategoryDao {
//
//    private static final Logger logger = LogManager.getLogger();
//
//    @Autowired
//    private MessageContainer messageContainer;
//
//    @Autowired
//    private SqlContainer sqlContainer;
//
//    public CategoryDaoImpl() {
//        super();
//    }
//
//    public CategoryDaoImpl(Connection connection) {
//        super(connection);
//    }
//
//    private final String INSERT_QUERY = sqlContainer.getSqlProperties()
//            .getProperty("INSERT_CATEGORY");
//
//    @Override
//    public Category add(Category category) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[]{"id"})) {
//            statement.setString(1, category.getName());
//            if (category.getParentCategory().getId() == 0) {
//                statement.setNull(2, Types.INTEGER);
//            } else {
//                statement.setInt(2, category.getParentCategory().getId());
//            }
//            statement.executeUpdate();
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                category.setId(generatedKeys.getInt(1));
//            }
//            return category;
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            Throwable cause = e.getCause();
//            if (cause != null && cause.getMessage().startsWith("Duplicate entry")) {
//                throw new ApplicationException(messageContainer.getMessageProperties()
//                        .getProperty("NOT_UNIQUE_CATEGORY"), e);
//            }
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("FAILED_INSERT_CATEGORY"), e);
//        }
//    }
//
//    private final String UPDATE_QUERY = sqlContainer.getSqlProperties()
//            .getProperty("UPDATE_CATEGORY");
//
//    @Override
//    public int update(Category category) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
//            statement.setString(1, category.getName());
//            if (category.getParentCategory().getId() == 0) {
//                statement.setNull(2, Types.INTEGER);
//            } else {
//                statement.setInt(2, category.getParentCategory().getId());
//            }
//            statement.setInt(3, category.getId());
//            return statement.executeUpdate();
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("FAILED_UPDATE_CATEGORY"), e);
//        }
//    }
//
//    private final String DELETE_QUERY = sqlContainer.getSqlProperties()
//            .getProperty("DELETE_CATEGORY");
//
//    @Override
//    public int delete(int id) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
//            statement.setInt(1, id);
//            return statement.executeUpdate();
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("FAILED_DELETE_CATEGORY"), e);
//        }
//    }
//
//    private final String SELECT_BY_ID_QUERY = sqlContainer.getSqlProperties()
//            .getProperty("SELECT_CATEGORY_BY_ID");
//
//    @Override
//    public Category getById(int id) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            return (Category) MapperHolder.getMapper(MapperHolder.CATEGORY).mapRow(resultSet);
//        } catch (ApplicationException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("EMPTY_RESULTSET") + Category.class, e);
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("FAILED_GET_CATEGORY"), e);
//        }
//    }
//
//    private final String DELETE_SUBCATEGORIES_QUERY = sqlContainer.getSqlProperties()
//            .getProperty("DELETE_SUBCATEGORIES");
//
//    @Override
//    public int deleteAllByParentId(int parentId) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(DELETE_SUBCATEGORIES_QUERY)) {
//            statement.setInt(1, parentId);
//            return statement.executeUpdate();
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("FAILED_DELETE_SUBCATEGORIES"), e);
//        }
//    }
//
//    private final String SELECT_SUBCATEGORIES_QUERY = sqlContainer.getSqlProperties()
//            .getProperty("SELECT_SUBCATEGORIES");
//
//    @Override
//    public List<Category> getAllByParentId(int parentId) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(SELECT_SUBCATEGORIES_QUERY)) {
//            statement.setInt(1, parentId);
//            ResultSet resultSet = statement.executeQuery();
//            return (List<Category>) MapperHolder.getMapper(MapperHolder.CATEGORY).mapAllRows(resultSet);
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("FAILED_GET_ALL_SUBCATEGORIES"), e);
//        }
//    }
//
//    private final String SELECT_ALL_QUERY = sqlContainer.getSqlProperties()
//            .getProperty("SELECT_ALL_CATEGORIES");
//
//    @Override
//    public List<Category> getAllByLevel(int level) throws ApplicationException {
//        String selectByLevel = prepareQueryForCategoryLevel(level);
//        try (PreparedStatement statement = connection.prepareStatement(selectByLevel)) {
//            ResultSet resultSet = statement.executeQuery();
//            return (List<Category>) MapperHolder.getMapper(MapperHolder.CATEGORY).mapAllRows(resultSet);
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("FAILED_GET_CATEGORIES_BY_LEVEL"), e);
//        }
//    }
//
//    private String prepareQueryForCategoryLevel(int level) {
//        if (level == 0) {
//            return SELECT_ALL_QUERY + " WHERE category_parent_id IS NULL ORDER BY category_name";
//        } else if (level == 1) {
//            return SELECT_ALL_QUERY + " WHERE category_parent_id > 0 ORDER BY category_name";
//        } else {
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("UNSUPPORTED_CATEGORY_LEVEL") + level);
//        }
//    }
//}