package ua.levelup.service;

import ua.levelup.web.dto.create.OrderCreateDto;
import ua.levelup.web.dto.view.CartViewDto;
import ua.levelup.web.dto.view.OrderViewDto;

import java.util.List;

public interface OrderService {
    OrderViewDto create(OrderCreateDto orderCreateDto, CartViewDto cartViewDto);

    OrderViewDto update(OrderCreateDto orderCreateDto, int orderId);

    void delete(int orderId);

    OrderViewDto get(int orderId);

    List<OrderViewDto> getUsersOrders(int userId);

    List<OrderViewDto> getAllOrders();
}