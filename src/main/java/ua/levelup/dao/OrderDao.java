package ua.levelup.dao;

import ua.levelup.exception.ApplicationException;
import ua.levelup.model.Order;

import java.util.List;

public interface OrderDao {

    Order add(Order order) throws ApplicationException;

    int update(Order order) throws ApplicationException;

    int delete(int orderId) throws ApplicationException;

    Order getById(int orderId) throws ApplicationException;

    List<Order> getAllByUserId(int userId) throws ApplicationException;

    List<Order> getAll() throws ApplicationException;
}
