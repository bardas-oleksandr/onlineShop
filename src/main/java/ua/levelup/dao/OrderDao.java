package ua.levelup.dao;

import lombok.NonNull;
import ua.levelup.exception.ApplicationException;
import ua.levelup.model.Order;

import java.util.List;

public interface OrderDao {

    void add(@NonNull Order order) throws ApplicationException;

    void update(@NonNull Order order) throws ApplicationException;

    void delete(int orderId) throws ApplicationException;

    Order getById(int orderId) throws ApplicationException;

    List<Order> getAllByUserId(int userId) throws ApplicationException;

    List<Order> getAll() throws ApplicationException;
}
