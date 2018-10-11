package ua.levelup.dao.impl;

import lombok.NonNull;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.levelup.dao.ProductDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.logger.ShopLogger;
import ua.levelup.model.Category;
import ua.levelup.model.Manufacturer;
import ua.levelup.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.levelup.model.SearchParams;

import java.sql.*;
import java.util.List;

public class ProductDaoImpl extends AbstractDaoImpl implements ProductDao, ShopLogger {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(@NonNull Product product) throws ApplicationException {
        String sql = "INSERT INTO products " +
                "(product_name, product_price, product_available, product_description" +
                ", product_category_id, product_manufacturer_id) " +
                "VALUES (:name,:price,:available,:description,:category_id,:manufacturer_id)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", product.getName());
        source.addValue("price", product.getPrice());
        source.addValue("available", product.isAvailable());
        source.addValue("description", product.getDescription());
        source.addValue("category_id", product.getCategory().getId());
        source.addValue("manufacturer_id", product.getManufacturer().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            namedParameterJdbcTemplate.update(sql, source, keyHolder);
            product.setId(keyHolder.getKey().intValue());
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("NOT_UNIQUE_PRODUCT"), e);
        } catch (DataIntegrityViolationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("FAILED_SAVE_PRODUCT"), e);
        }
    }

    @Override
    public void update(Product product) throws ApplicationException {
        String sql = "UPDATE products SET product_name=:product_name, product_price=:product_price" +
                ", product_available=:product_available, product_description=:product_description" +
                ", product_category_id=:product_category_id, product_manufacturer_id=:product_manufacturer_id " +
                "WHERE id=:id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", product.getName());
        source.addValue("price", product.getPrice());
        source.addValue("available", product.isAvailable());
        source.addValue("description", product.getDescription());
        source.addValue("category_id", product.getCategory().getId());
        source.addValue("manufacturer_id", product.getManufacturer().getId());
        source.addValue("id", product.getId());

        try {
            namedParameterJdbcTemplate.update(sql, source);
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("NOT_UNIQUE_PRODUCT"), e);
        } catch (DataIntegrityViolationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("FAILED_SAVE_PRODUCT"), e);
        }
    }

    @Override
    public void delete(int id) throws ApplicationException {
        String sql = "DELETE FROM products WHERE id=:id";

        try {
            if (namedParameterJdbcTemplate.update(sql
                    , new MapSqlParameterSource("id", id)) == 0) {
                throw new ApplicationException(messagesProperties
                        .getProperty("FAILED_DELETE_PRODUCT_NONEXISTENT"));
            }
        } catch (DataIntegrityViolationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("INTEGRITY_VIOLATION_WHILE_DELETE_PRODUCT"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_DELETE_PRODUCT"), e);
        }
    }

    @Override
    public Product getById(int id) throws ApplicationException {
        String sql = "SELECT products.id, products.product_name, products.product_price" +
                ", products.product_available, products.product_description, products.product_category_id" +
                ", cat1.category_name, cat1.category_parent_id, products.product_manufacturer_id" +
                ", manufacturers.manufacturer_name, cat2.category_name " +
                "FROM products, categories cat1, manufacturers " +
                "LEFT JOIN categories cat2 ON cat1.category_parent_id=cat2.id " +
                "WHERE products.product_category_id = cat1.id " +
                "AND products.product_manufacturer_id = manufacturers.id " +
                "AND products.id=:id ";

        try {
            return namedParameterJdbcTemplate.queryForObject(sql
                    , new MapSqlParameterSource("id", id)
                    , new ProductMapper());
        } catch (EmptyResultDataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("EMPTY_RESULTSET") + Product.class, e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_GET_PRODUCT"), e);
        }
    }

    @Override
    public List<Product> getFilteredProducts(SearchParams searchParams)
            throws ApplicationException {

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("min_price", searchParams.getMinPrice());
        source.addValue("max_price", searchParams.getMaxPrice());

        try {
            return namedParameterJdbcTemplate.query(getSelectQuery(searchParams), source
                    , new ProductMapper());
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_GET_FILTERED_PRODUCTS"), e);
        }
    }

    private String getSelectQuery(SearchParams searchParams) {
        String sql = "SELECT products.id, products.product_name, products.product_price" +
                ", products.product_available, products.product_description, products.product_category_id" +
                ", cat1.category_name, cat1.category_parent_id, products.product_manufacturer_id" +
                ", manufacturers.manufacturer_name, cat2.category_name " +
                "FROM products, categories cat1, manufacturers " +
                "LEFT JOIN categories cat2 ON cat1.category_parent_id=cat2.id " +
                "WHERE products.product_category_id = cat1.id " +
                "AND products.product_manufacturer_id = manufacturers.id " +
                "AND product_price >=:min_price " +
                "AND product_price <=:max_price";

        StringBuilder query = new StringBuilder(sql);
        if (searchParams.isAvailableOnly()) {
            query.append(" AND products.product_available = " + searchParams.isAvailableOnly());
        }
        if (searchParams.getManufacturerId() != 0) {
            query.append(" AND products.product_manufacturer_id = " + searchParams.getManufacturerId());
        }
        if (searchParams.getCategoryId() != 0) {
            query.append(" AND (products.product_category_id = " + searchParams.getCategoryId());
            query.append(" OR products.product_category_id " +
                    "IN (SELECT categories.id FROM categories WHERE categories.category_parent_id = "
                    + searchParams.getCategoryId() + "))");
        }
        switch (searchParams.getOrderMethod()) {
            case PRODUCT_NAME:
                query.append(" ORDER BY product_name");
                break;
            case CHEAP_FIRST:
                query.append(" ORDER BY product_price");
                break;
            case CHEAP_LAST:
                query.append(" ORDER BY product_price DESC");
                break;
        }
        return query.toString();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    private class ProductMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            Product product = new Product();
            product.setId(resultSet.getInt(1));
            product.setName(resultSet.getString(2));
            product.setPrice(resultSet.getFloat(3));
            product.setAvailable(resultSet.getBoolean(4));
            product.setDescription(resultSet.getString(5));

            Category category = new Category();
            category.setId(resultSet.getInt(6));
            category.setName(resultSet.getString(7));
            Category parent = new Category();
            parent.setId(resultSet.getInt(8));
            parent.setName(resultSet.getString(11));
            category.setParentCategory(parent);
            product.setCategory(category);

            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(resultSet.getInt(9));
            manufacturer.setName(resultSet.getString(10));
            product.setManufacturer(manufacturer);

            return product;
        }
    }
}