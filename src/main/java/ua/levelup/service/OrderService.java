package ua.levelup.service;

import ua.levelup.model.Order;
import ua.levelup.web.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(Order order);
    List<OrderDto> getUsersOrders(int userId);
    void updateOrder(Order order, int orderId);
    List<OrderDto> getAllOrders();
}
