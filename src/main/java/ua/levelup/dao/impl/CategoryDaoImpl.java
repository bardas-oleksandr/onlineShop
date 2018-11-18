package ua.levelup.dao.impl;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.levelup.dao.CategoryDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.logger.ShopLogger;
import ua.levelup.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
@Repository("categoryDao")
public class CategoryDaoImpl extends AbstractDaoImpl implements CategoryDao, ShopLogger {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(@NonNull Category category) throws ApplicationException {
        String sql = "INSERT INTO categories (category_name, category_parent_id) " +
                "VALUES (:category_name,:category_parent_id)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("category_name", category.getName());
        Integer parentId = null;
        if (category.getParentCategory() != null && category.getParentCategory().getId() != 0) {
            parentId = category.getParentCategory().getId();
        }
        source.addValue("category_parent_id", parentId);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            namedParameterJdbcTemplate.update(sql, source, keyHolder);
            category.setId((keyHolder.getKey().intValue()));
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("NOT_UNIQUE_CATEGORY"), e);
        } catch (DataIntegrityViolationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("DATA_INTEGRITY_VIOLATION_FOR_CATEGORY"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("FAILED_SAVE_CATEGORY"), e);
        }
    }

    @Override
    public void update(@NonNull Category category) throws ApplicationException {
        String sql = "UPDATE categories " +
                "SET category_name=:category_name, category_parent_id=:category_parent_id " +
                "WHERE id=:id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("category_name", category.getName());
        Integer parentId = null;
        if (category.getParentCategory() != null) {
            parentId = category.getParentCategory().getId();
        }
        source.addValue("category_parent_id", parentId);
        source.addValue("id", category.getId());

        try {
            updateOrDelete(sql, source);
        } catch (DuplicateKeyException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("NOT_UNIQUE_CATEGORY"), e);
        } catch (DataIntegrityViolationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("DATA_INTEGRITY_VIOLATION_FOR_CATEGORY"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("FAILED_SAVE_CATEGORY"), e);
        }
    }

    @Override
    public void delete(int id) throws ApplicationException {
        String sql = "DELETE FROM categories WHERE id = :id";

        MapSqlParameterSource source = new MapSqlParameterSource("id", id);

        try {
            updateOrDelete(sql, source);
        } catch (DataIntegrityViolationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("INTEGRITY_VIOLATION_WHILE_DELETE_CATEGORY"), e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_DELETE_CATEGORY"), e);
        }
    }

    @Override
    public Category getById(int id) throws ApplicationException {
        String sql = "SELECT cat1.id, cat1.category_name, cat1.category_parent_id, " +
                "cat2.category_name, cat2.category_parent_id " +
                "FROM categories cat1 " +
                "LEFT JOIN categories cat2 " +
                "ON cat1.category_parent_id=cat2.id " +
                "WHERE cat1.id=:id";

        try {
            return namedParameterJdbcTemplate.queryForObject(sql
                    , new MapSqlParameterSource("id", id)
                    , new CategoryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("EMPTY_RESULTSET")
                    + Category.class, e);
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("FAILED_GET_CATEGORY"), e);
        }
    }

    @Override
    public int deleteAllByParentId(int parentId) throws ApplicationException {
        String sql = "DELETE FROM categories WHERE category_parent_id=:category_parent_id";

        try {
            return namedParameterJdbcTemplate.update(sql
                    , new MapSqlParameterSource("category_parent_id", parentId));
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties.getProperty("FAILED_DELETE_SUBCATEGORIES"), e);
        }
    }

    @Override
    public List<Category> getAllByParentId(int parentId) throws ApplicationException {
        String sql = "SELECT cat1.id, cat1.category_name, cat1.category_parent_id" +
                ",cat2.category_name, cat2.category_parent_id FROM categories cat1 " +
                "LEFT JOIN categories cat2 " +
                "ON cat1.category_parent_id=cat2.id " +
                "WHERE cat1.category_parent_id=:category_parent_id " +
                "ORDER BY cat1.category_name";

        try {
            return namedParameterJdbcTemplate.query(sql
                    , new MapSqlParameterSource("category_parent_id", parentId)
                    , new CategoryRowMapper());
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_GET_SUBCATEGORIES"), e);
        }
    }

    @Override
    public List<Category> getAllByLevel(int level) throws ApplicationException {
        String sql = prepareQueryForCategoryLevel(level);

        try {
            return namedParameterJdbcTemplate.query(sql, new CategoryRowMapper());
        } catch (DataAccessException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_GET_CATEGORIES_BY_LEVEL"), e);
        }
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    private void updateOrDelete(String sql, MapSqlParameterSource source) {
        if (namedParameterJdbcTemplate.update(sql, source) == 0) {
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_UPDATE_CATEGORY_NONEXISTENT"));
        }
    }

    private String prepareQueryForCategoryLevel(int level) {
        String sql = "SELECT cat1.id, cat1.category_name, cat1.category_parent_id, " +
                "cat2.category_name, cat2.category_parent_id " +
                "FROM categories cat1 " +
                "LEFT JOIN categories cat2 " +
                "ON cat1.category_parent_id = cat2.id";

        if (level == 0) {
            return sql + " WHERE cat1.category_parent_id IS NULL ORDER BY cat1.category_name";
        } else if (level == 1) {
            return sql + " WHERE cat1.category_parent_id > 0 ORDER BY cat1.category_name";
        } else {
            throw new ApplicationException(messagesProperties
                    .getProperty("UNSUPPORTED_CATEGORY_LEVEL") + level);
        }
    }

    /**
     *
     */
    private class CategoryRowMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet resultSet, int i) throws SQLException {
            Category category = new Category();
            category.setId(resultSet.getInt(1));
            category.setName(resultSet.getString(2));
            Integer parentCategoryId = resultSet.getInt(3);
            if (parentCategoryId != null && parentCategoryId != 0) {
                Category parent = new Category();
                parent.setId(parentCategoryId);
                parent.setName(resultSet.getString(4));
                category.setParentCategory(parent);
            }
            return category;
        }
    }
}