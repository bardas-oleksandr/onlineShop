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
import ua.levelup.dao.ManufacturerDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.logger.ShopLogger;
import ua.levelup.model.Manufacturer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 *
 */
@Repository("manufacturerDao")
public class ManufacturerDaoImpl extends AbstractDaoImpl implements ManufacturerDao, ShopLogger {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(@NonNull Manufacturer manufacturer) throws ApplicationException {
        String sql = "INSERT INTO manufacturers (manufacturer_name) VALUES (:manufacturer_name)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = new MapSqlParameterSource("manufacturer_name"
                , manufacturer.getName());

        try {
            namedParameterJdbcTemplate.update(sql, source, keyHolder);
            manufacturer.setId(keyHolder.getKey().intValue());
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("NOT_UNIQUE_MANUFACTURER"), e);
        } catch (DataIntegrityViolationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("DATA_INTEGRITY_VIOLATION_FOR_MANUFACTURER"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_SAVE_MANUFACTURER"), e);
        }
    }

    @Override
    public void update(@NonNull Manufacturer manufacturer) throws ApplicationException {
        String sql = "UPDATE manufacturers SET manufacturer_name=:manufacturer_name WHERE id=:id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("manufacturer_name", manufacturer.getName());
        source.addValue("id", manufacturer.getId());

        try {
            updateOrDelete(sql, source);
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("NOT_UNIQUE_MANUFACTURER"), e);
        }catch (DataIntegrityViolationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("DATA_INTEGRITY_VIOLATION_FOR_MANUFACTURER"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_SAVE_MANUFACTURER"), e);
        }
    }

    @Override
    public void delete(int id) throws ApplicationException {
        String sql = "DELETE FROM manufacturers WHERE id=:id";

        MapSqlParameterSource source = new MapSqlParameterSource("id", id);

        try {
            updateOrDelete(sql, source);
        } catch (DataIntegrityViolationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.
                    getProperty("INTEGRITY_VIOLATION_WHILE_DELETE_MANUFACTURER"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_DELETE_MANUFACTURER"), e);
        }
    }

    @Override
    public Manufacturer getById(int id) throws ApplicationException {
        String sql = "SELECT * FROM manufacturers WHERE id=:id";

        try {
            return namedParameterJdbcTemplate.queryForObject(sql
                    , new MapSqlParameterSource("id", id)
                    , new ManufacturerRowMapper());
        } catch (EmptyResultDataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("EMPTY_RESULTSET")
                    + Manufacturer.class, e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("FAILED_GET_MANUFACTURER"), e);
        }
    }

    @Override
    public List<Manufacturer> getAllByCategoryId(int categoryId) throws ApplicationException {
        //Запрос для выборки всех производителей,
        //для которых в базе данных существуют связанные товары,
        //нахоядищееся в категории или подкатегории товаров с заданным id.
        String sql = "SELECT * FROM manufacturers " +
                "WHERE manufacturers.id " +
                "IN (SELECT product_manufacturer_id FROM products, categories " +
                "WHERE products.product_category_id=:product_category_id " +
                "OR (products.product_category_id=categories.id " +
                "AND categories.category_parent_id=:product_category_id)) " +
                "ORDER BY manufacturer_name";

        try {
            return namedParameterJdbcTemplate.query(sql,
                    new MapSqlParameterSource("product_category_id", categoryId),
                    new ManufacturerRowMapper());
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_GET_MANUFACTURERS"), e);
        }
    }

    @Override
    public List<Manufacturer> getAll() throws ApplicationException {
        String sql = "SELECT * FROM manufacturers ORDER BY manufacturer_name";

        try {
            return namedParameterJdbcTemplate.query(sql, new ManufacturerRowMapper());
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
                    .getProperty("FAILED_UPDATE_MANUFACTURER_NONEXISTENT");
            logErrorMessage(message);
            throw new ApplicationException(message);
        }
    }

    /**
     *
     */
    private class ManufacturerRowMapper implements RowMapper<Manufacturer> {

        @Override
        public Manufacturer mapRow(ResultSet resultSet, int i) throws SQLException {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(resultSet.getInt(1));
            manufacturer.setName(resultSet.getString(2));
            return manufacturer;
        }
    }
}