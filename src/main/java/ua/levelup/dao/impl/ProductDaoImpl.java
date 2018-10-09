//package ua.levelup.dao.impl;
//
//import ua.levelup.dao.ProductDao;
//import ua.levelup.dao.support.OrderMethod;
//import ua.levelup.dao.support.SqlContainer;
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.support.MessageContainer;
//import ua.levelup.mapper.ProductMapper;
//import ua.levelup.model.Product;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.sql.*;
//import java.util.List;
//
//public class ProductDaoImpl extends AbstractDaoImpl implements ProductDao {
//
//    private static final Logger logger = LogManager.getLogger();
//
//    public ProductDaoImpl() {
//        super();
//    }
//
//    public ProductDaoImpl(Connection connection) {
//        super(connection);
//    }
//
//    private final static String INSERT_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("INSERT_PRODUCT");
//
//    @Override
//    public Product add(Product product) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[]{"id"})) {
//            statement.setString(1, product.getName());
//            statement.setFloat(2, product.getPrice());
//            statement.setBoolean(3, product.isAvailable());
//            if (!StringUtils.isEmpty(product.getDescription())) {
//                statement.setString(4, product.getDescription());
//            } else {
//                statement.setNull(4, Types.VARCHAR);
//            }
//            statement.setInt(5, product.getCategory().getId());
//            statement.setInt(6, product.getManufacturer().getId());
//            statement.executeUpdate();
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                product.setId(generatedKeys.getInt(1));
//            }
//            return product;
//        } catch (SQLException e) {
//            Throwable cause = e.getCause();
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            if (cause != null && cause.getMessage().startsWith("Duplicate entry")) {
//                throw new ApplicationException(MessageContainer.getMessageProperties()
//                        .getProperty("NOT_UNIQUE_PRODUCT"), e);
//            }
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_INSERT_PRODUCT"), e);
//        }
//    }
//
//    private final static String UPDATE_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("UPDATE_PRODUCT");
//
//    @Override
//    public int update(Product product) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
//            statement.setString(1, product.getName());
//            statement.setFloat(2, product.getPrice());
//            statement.setBoolean(3, product.isAvailable());
//            if (!StringUtils.isEmpty(product.getDescription())) {
//                statement.setString(4, product.getDescription());
//            } else {
//                statement.setNull(4, Types.VARCHAR);
//            }
//            statement.setInt(5, product.getCategory().getId());
//            statement.setInt(6, product.getManufacturer().getId());
//            statement.setInt(7, product.getId());
//            return statement.executeUpdate();
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_UPDATE_PRODUCT"), e);
//        }
//    }
//
//    private final static String DELETE_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("DELETE_PRODUCT");
//
//    @Override
//    public int delete(int id) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
//            statement.setInt(1, id);
//            return statement.executeUpdate();
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_DELETE_PRODUCT"), e);
//        }
//    }
//
//    private final static String SELECT_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("SELECT_PRODUCT");
//
//    @Override
//    public Product getById(int id) throws ApplicationException {
//        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY)) {
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            return new ProductMapper().mapRow(resultSet);
//        } catch (ApplicationException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("EMPTY_RESULTSET") + Product.class, e);
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_GET_PRODUCT"), e);
//        }
//    }
//
//    @Override
//    public List<Product> getFilteredProducts(Product product, float minPrice, float maxPrice,
//                                             OrderMethod method)
//            throws ApplicationException {
//        String query = getSelectQuery(product, method);
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setFloat(1, minPrice);
//            statement.setFloat(2, maxPrice);
//            ResultSet resultSet = statement.executeQuery();
//            return new ProductMapper().mapAllRows(resultSet);
//        } catch (SQLException e) {
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(MessageContainer.getMessageProperties()
//                    .getProperty("FAILED_GET_FILTERED_PRODUCTS"), e);
//        }
//    }
//
//    private final static String SELECT_FILTERED_PRODUCTS_QUERY = SqlContainer.getSqlProperties()
//            .getProperty("SELECT_FILTERED_PRODUCTS");
//
//    private String getSelectQuery(Product product, OrderMethod method) {
//        StringBuilder query = new StringBuilder(SELECT_FILTERED_PRODUCTS_QUERY);
//        if (!StringUtils.isEmpty(product.getName())) {
//            query.append(" AND products.product_name = " + product.getName());
//        }
//        if (product.isAvailable()) {
//            query.append(" AND products.product_available = " + product.isAvailable());
//        }
//        if (product.getManufacturer().getId() != 0) {
//            query.append(" AND products.product_manufacturer_id = " + product.getManufacturer().getId());
//        }
//        if (product.getCategory().getId() != 0) {
//            query.append(" AND (products.product_category_id = " + product.getCategory().getId());
//            query.append(" OR products.product_category_id " +
//                    "IN (SELECT categories.id FROM categories WHERE categories.category_parent_id = "
//                    + product.getCategory().getId() + "))");
//        }
//        switch (method) {
//            case PRODUCT_NAME:
//                query.append(" ORDER BY product_name");
//                break;
//            case CHEAP_FIRST:
//                query.append(" ORDER BY product_price");
//                break;
//            case CHEAP_LAST:
//                query.append(" ORDER BY product_price DESC");
//                break;
//        }
//        return query.toString();
//    }
//}
