package ua.levelup.service;

import ua.levelup.web.dto.create.OrderCreateDto;
import ua.levelup.web.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderCreateDto orderCreateDto);
    List<OrderDto> getUsersOrders(int userId);
    void updateOrder(OrderCreateDto orderCreateDto, int orderId);
    List<OrderDto> getAllOrders();
}
