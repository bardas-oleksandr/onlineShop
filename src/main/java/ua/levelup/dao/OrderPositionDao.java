package ua.levelup.dao;

import ua.levelup.exception.ApplicationException;
import ua.levelup.model.OrderPosition;

import java.util.List;

public interface OrderPositionDao {

    void addAll(List<OrderPosition> orderPositionList) throws ApplicationException;

    int update(int orderId, int productId, OrderPosition orderPosition) throws ApplicationException;

    int delete(int orderId, int productId) throws ApplicationException;

    OrderPosition getByPrimaryKey(int orderId, int productId) throws ApplicationException;

    int deleteAllByOrderId(int orderId) throws ApplicationException;

    List<OrderPosition> getAllByOrderId(int orderId) throws ApplicationException;
}
