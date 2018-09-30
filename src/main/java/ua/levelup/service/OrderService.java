package ua.levelup.service;

import ua.levelup.web.dto.OrderCreateDto;
import ua.levelup.web.dto.OrderViewDto;

import java.util.List;

public interface OrderService {
    OrderViewDto createOrder(OrderCreateDto orderCreateDto);
    List<OrderViewDto> getUsersOrders(int userId);
    void updateOrder(OrderCreateDto orderCreateDto, int orderId);
    List<OrderViewDto> getAllOrders();
}
