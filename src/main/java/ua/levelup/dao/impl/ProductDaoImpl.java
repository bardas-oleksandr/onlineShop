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
import org.springframework.stereotype.Repository;
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

/**
 *
 */
@Repository("productDao")
public class ProductDaoImpl extends AbstractDaoImpl implements ProductDao, ShopLogger {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(@NonNull Product product) throws ApplicationException {
        String sql = "INSERT INTO products " +
                "(product_name, product_price, product_available, product_description" +
                ", product_category_id, product_manufacturer_id) " +
                "VALUES (:name,:price,:available,:description,:category_id,:manufacturer_id)";

        try {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("name", product.getName());
            source.addValue("price", product.getPrice());
            source.addValue("available", product.isAvailable());
            source.addValue("description", product.getDescription());
            source.addValue("category_id", product.getCategory().getId());
            source.addValue("manufacturer_id", product.getManufacturer().getId());
            KeyHolder keyHolder = new GeneratedKeyHolder();

            namedParameterJdbcTemplate.update(sql, source, keyHolder);
            product.setId(keyHolder.getKey().intValue());
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("NOT_UNIQUE_PRODUCT"), e);
        } catch (DataIntegrityViolationException | NullPointerException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("FAILED_SAVE_PRODUCT"), e);
        }
    }

    @Override
    public void update(@NonNull Product product) throws ApplicationException {
        String sql = "UPDATE products SET product_name=:name, product_price=:price" +
                ", product_available=:available, product_description=:description" +
                ", product_category_id=:category_id, product_manufacturer_id=:manufacturer_id " +
                "WHERE id=:id";

        try {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("name", product.getName());
            source.addValue("price", product.getPrice());
            source.addValue("available", product.isAvailable());
            source.addValue("description", product.getDescription());
            source.addValue("category_id", product.getCategory().getId());
            source.addValue("manufacturer_id", product.getManufacturer().getId());
            source.addValue("id", product.getId());

            updateOrDelete(sql, source);
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("NOT_UNIQUE_PRODUCT"), e);
        } catch (DataIntegrityViolationException | NullPointerException e) {
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

        MapSqlParameterSource source = new MapSqlParameterSource("id", id);

        try {
            updateOrDelete(sql, source);
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
        String sql = "SELECT p.id, p.product_name, p.product_price, p.product_available" +
                ", p.product_description, p.product_category_id, cat1.category_name" +
                ", cat1.category_parent_id, p.product_manufacturer_id" +
                ", m.manufacturer_name, cat2.category_name " +
                "FROM products p " +
                "INNER JOIN categories cat1 ON p.product_category_id = cat1.id " +
                "INNER JOIN manufacturers m ON p.product_manufacturer_id = m.id " +
                "LEFT JOIN categories cat2 ON cat1.category_parent_id=cat2.id " +
                "WHERE p.id=:id";

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

    @Override
    public Logger getLogger() {
        return logger;
    }

    private String getSelectQuery(SearchParams searchParams) {
        String sql = "SELECT p.id, p.product_name, p.product_price" +
                ", p.product_available, p.product_description, p.product_category_id" +
                ", cat1.category_name, cat1.category_parent_id, p.product_manufacturer_id" +
                ", m.manufacturer_name, cat2.category_name " +
                "FROM products p " +
                "INNER JOIN categories cat1 ON p.product_category_id = cat1.id " +
                "INNER JOIN manufacturers m ON p.product_manufacturer_id = m.id " +
                "LEFT JOIN categories cat2 ON cat1.category_parent_id=cat2.id " +
                "WHERE p.product_price >=:min_price " +
                "AND p.product_price <=:max_price ";

        StringBuilder query = new StringBuilder(sql);
        if (searchParams.isAvailableOnly()) {
            query.append(" AND p.product_available = " + searchParams.isAvailableOnly());
        }
        if (searchParams.getManufacturerId() != 0) {
            query.append(" AND p.product_manufacturer_id = " + searchParams.getManufacturerId());
        }
        if (searchParams.getCategoryId() != 0) {
            query.append(" AND (p.product_category_id = " + searchParams.getCategoryId());
            query.append(" OR p.product_category_id " +
                    "IN (SELECT categories.id FROM categories WHERE categories.category_parent_id = "
                    + searchParams.getCategoryId() + "))");
        }
        switch (searchParams.getOrderMethod()) {
            case PRODUCT_NAME:
                query.append(" ORDER BY p.product_name");
                break;
            case CHEAP_FIRST:
                query.append(" ORDER BY p.product_price");
                break;
            case CHEAP_LAST:
                query.append(" ORDER BY p.product_price DESC");
                break;
        }
        return query.toString();
    }

    private void updateOrDelete(String sql, MapSqlParameterSource source) {
        if (namedParameterJdbcTemplate.update(sql, source) == 0) {
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_UPDATE_PRODUCT_NONEXISTENT"));
        }
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