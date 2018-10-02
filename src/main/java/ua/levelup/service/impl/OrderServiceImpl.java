package ua.levelup.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.levelup.converter.OrderConverter;
import ua.levelup.dao.OrderDao;
import ua.levelup.dao.OrderPositionDao;
import ua.levelup.model.Order;
import ua.levelup.model.OrderPosition;
import ua.levelup.service.OrderService;
import ua.levelup.validator.OrderValidator;
import ua.levelup.web.dto.OrderDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Service("orderService")
@Getter
@Setter
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderPositionDao orderPositionDao;

    @Override
    public OrderDto createOrder(Order order) {
        OrderValidator.validateOrderWithOrderPositionList(order);
        order = orderDao.add(order);
        orderPositionDao.addAll(order.getOrderPositionList());
        return OrderConverter.asOrderDto(order);
    }

    @Override
    public List<OrderDto> getUsersOrders(int userId) {
        List<Order> orderList = orderDao.getAllByUserId(userId);
        return getOrderDtoList(orderList);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orderList = orderDao.getAll();
        return getOrderDtoList(orderList);
    }

    @Override
    public void updateOrder(Order order, int orderId) {
        OrderValidator.validateOrder(order);
        order.setId(orderId);
        orderDao.update(order);
    }

    private List<OrderDto> getOrderDtoList(List<Order> orderList){
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order: orderList) {
            List<OrderPosition> orderPositionList = orderPositionDao.getAllByOrderId(order.getId());
            order.setOrderPositionList(orderPositionList);
            OrderDto orderDto = OrderConverter.asOrderDto(order);
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }
}
