package ua.levelup.dao.impl;

import ua.levelup.dao.UserDao;
import ua.levelup.dao.support.SqlHolder;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.mapper.support.MapperHolder;
import ua.levelup.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class UserDaoImpl extends AbstractDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger();

    public UserDaoImpl() {
        super();
    }

    public UserDaoImpl(Connection connection) {
        super(connection);
    }

    private final static String INSERT_QUERY = SqlHolder.getSqlProperties()
            .getProperty("INSERT_USER");
    @Override
    public User add(User user) throws ApplicationException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[]{"id"})) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getUserState().ordinal());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            Throwable cause = e.getCause();
            if(cause!= null && cause.getMessage().startsWith("Duplicate entry")){
                throw new ApplicationException(MessageHolder.getMessageProperties()
                        .getProperty("NOT_UNIQUE_USER"), e);
            }
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_INSERT_USER"), e);
        }
    }

    private final static String UPDATE_QUERY = SqlHolder.getSqlProperties()
            .getProperty("UPDATE_USER");
    @Override
    public int update(int id, User user) throws ApplicationException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getUserState().ordinal());
            statement.setInt(5, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_UPDATE_USER"), e);
        }
    }

    private final static String DELETE_QUERY = SqlHolder.getSqlProperties()
            .getProperty("DELETE_USER");
    @Override
    public int delete(int id) throws ApplicationException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_DELETE_USER"), e);
        }
    }

    private final static String SELECT_BY_ID_QUERY = SqlHolder.getSqlProperties()
            .getProperty("SELECT_USER_BY_ID");
    @Override
    public User getById(int id) throws ApplicationException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return (User) MapperHolder.getMapper(MapperHolder.USER).mapRow(resultSet);
        } catch(ApplicationException e){
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("EMPTY_RESULTSET") + User.class, e);
        }
        catch(SQLException e) {
            logger.error(e.getMessage());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_GET_USER"), e);
        }
    }

    private final static String SELECT_BY_EMAIL_QUERY = SqlHolder.getSqlProperties()
            .getProperty("SELECT_USER_BY_EMAIL");
    @Override
    public User getByEmail(String email) throws ApplicationException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL_QUERY)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return (User) MapperHolder.getMapper(MapperHolder.USER).mapRow(resultSet);
        } catch(ApplicationException e){
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("EMPTY_RESULTSET") + User.class, e);
        }
        catch (SQLException e) {
            logger.error(e.getMessage());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_GET_USER"), e);
        }
    }

    private final static String SELECT_ALL_QUERY = SqlHolder.getSqlProperties()
            .getProperty("SELECT_ALL_USERS");
    @Override
    public List<User> getAllUsers() throws ApplicationException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            return (List<User>) MapperHolder.getMapper(MapperHolder.USER).mapAllRows(resultSet);
        } catch (SQLException e) {
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_GET_ALL_USERS"), e);
        }
    }
}
