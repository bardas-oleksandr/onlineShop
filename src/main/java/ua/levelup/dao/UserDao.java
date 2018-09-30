package ua.levelup.dao;

import ua.levelup.exception.ApplicationException;
import ua.levelup.model.User;

import java.util.List;

public interface UserDao{

    User add(User user) throws ApplicationException;

    int update(int id, User user) throws ApplicationException;

    int delete(int id) throws ApplicationException;

    User getById(int id) throws ApplicationException;

    User getByEmail(String email) throws ApplicationException;

    List<User> getAllUsers() throws ApplicationException;
}
