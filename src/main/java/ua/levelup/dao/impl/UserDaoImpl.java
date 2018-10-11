package ua.levelup.dao.impl;

import lombok.NonNull;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import ua.levelup.dao.UserDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.logger.ShopLogger;
import ua.levelup.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * По заданию работу с одной из сущностей надо организовать на "чистом" JDBC
 * Класс UserDaoImpl реализует доступ к данным сущности "Пользователь"
 */
public class UserDaoImpl implements UserDao, ShopLogger {

    private static final Logger logger = LogManager.getLogger();
    private static final int MAX_WAIT = 3000;

    @Autowired
    private Properties messagesProperties;

    @Autowired
    private GenericObjectPool<Connection> connectionPool;

    private Connection connection;

    @PostConstruct
    public void init() {
        try {
            connection = connectionPool.borrowObject(MAX_WAIT);
        } catch (Exception e) {
            logError(e);
            throw new ApplicationException(messagesProperties.
                    getProperty("FAILED_RETRIEVE_CONNECTION"));
        }
    }

    @Override
    public void add(@NonNull User user) throws ApplicationException {
        String sql = "INSERT INTO users (user_name, user_password, user_email, user_state) " +
                "VALUES (?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"})) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getUserState().ordinal());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            logError(e);
            Throwable cause = e.getCause();
            if (cause != null && cause.getMessage().startsWith("Duplicate entry")) {
                throw new ApplicationException(messagesProperties
                        .getProperty("NOT_UNIQUE_USER"), e);
            }
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_SAVE_USER"), e);
        }
    }

    @Override
    public void update(@NonNull User user) throws ApplicationException {
        String sql = "UPDATE users " +
                "SET user_name = ?, user_password = ?, user_email = ?, user_state = ? " +
                "WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getUserState().ordinal());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logError(e);
            Throwable cause = e.getCause();
            if (cause != null && cause.getMessage().startsWith("Duplicate entry")) {
                throw new ApplicationException(messagesProperties
                        .getProperty("NOT_UNIQUE_USER"), e);
            }
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_SAVE_USER"), e);
        }
    }

    @Override
    public void delete(int id) throws ApplicationException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            if(statement.executeUpdate() == 0){
                throw new ApplicationException(messagesProperties
                        .getProperty("FAILED_DELETE_USER_NONEXISTENT"));
            }
        } catch (SQLException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_DELETE_USER"), e);
        }
    }

    @Override
    public User getById(int id) throws ApplicationException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return new UserRowMapper().mapRow(resultSet, 1);
        } catch (ApplicationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("EMPTY_RESULTSET") + User.class, e);
        } catch (SQLException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_GET_USER"), e);
        }
    }

    @Override
    public User getByEmail(String email) throws ApplicationException {
        String sql = "SELECT * FROM users WHERE user_email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return new UserRowMapper().mapRow(resultSet, 1);
        } catch (ApplicationException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("EMPTY_RESULTSET") + User.class, e);
        } catch (SQLException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_GET_USER"), e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ApplicationException {
        String sql = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            return extractUserList(new UserRowMapper(), resultSet);
        } catch (SQLException e) {
            logError(e);
            throw new ApplicationException(messagesProperties
                    .getProperty("FAILED_GET_ALL_USERS"), e);
        }
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    private List<User> extractUserList(UserRowMapper mapper, ResultSet resultSet)
            throws SQLException {
        List<User> result = new ArrayList<>();
        int line = 1;
        while (resultSet.next()) {
            result.add(mapper.mapRow(resultSet, line++));
        }
        return result;
    }

    /**
     *
     */
    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            if (!resultSet.next()) {
                throw new ApplicationException(messagesProperties
                        .getProperty("EMPTY_RESULTSET") + User.class);
            }
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUserName(resultSet.getString("user_name"));
            user.setPassword(resultSet.getString("user_password"));
            user.setEmail(resultSet.getString("user_email"));
            user.setUserState(User.UserState.get(resultSet.getInt("user_state")));
            return user;
        }
    }
}