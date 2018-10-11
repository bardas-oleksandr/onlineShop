package ua.levelup.dao;

import lombok.NonNull;
import ua.levelup.exception.ApplicationException;
import ua.levelup.model.User;

import java.util.List;

public interface UserDao{

    void add(@NonNull User user) throws ApplicationException;

    void update(@NonNull User user) throws ApplicationException;

    void delete(int id) throws ApplicationException;

    User getById(int id) throws ApplicationException;

    User getByEmail(@NonNull String email) throws ApplicationException;

    List<User> getAllUsers() throws ApplicationException;
}
