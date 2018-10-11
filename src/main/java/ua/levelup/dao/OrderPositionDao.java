package ua.levelup.dao;

import lombok.NonNull;
import ua.levelup.exception.ApplicationException;
import ua.levelup.model.OrderPosition;

import java.util.List;

public interface OrderPositionDao {

    void addAll(@NonNull List<OrderPosition> orderPositionList) throws ApplicationException;

    void update(@NonNull OrderPosition orderPosition) throws ApplicationException;

    void delete(int orderId, int productId) throws ApplicationException;

    OrderPosition getByPrimaryKey(int orderId, int productId) throws ApplicationException;

    int deleteAllByOrderId(int orderId) throws ApplicationException;

    List<OrderPosition> getAllByOrderId(int orderId) throws ApplicationException;
}
