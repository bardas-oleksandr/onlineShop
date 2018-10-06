package ua.levelup.service;

import ua.levelup.model.Order;
import ua.levelup.web.dto.view.OrderViewDto;

import java.util.List;

public interface OrderService {
    OrderViewDto createOrder(Order order);
    List<OrderViewDto> getUsersOrders(int userId);
    void updateOrder(Order order, int orderId);
    List<OrderViewDto> getAllOrders();
}
