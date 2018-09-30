package ua.levelup.service.impl;

import ua.levelup.converter.OrderConverter;
import ua.levelup.converter.OrderPositionConverter;
import ua.levelup.dao.OrderDao;
import ua.levelup.dao.OrderPositionDao;
import ua.levelup.dao.support.DaoHolder;
import ua.levelup.model.Order;
import ua.levelup.model.OrderPosition;
import ua.levelup.service.OrderService;
import ua.levelup.validator.OrderValidator;
import ua.levelup.web.dto.OrderCreateDto;
import ua.levelup.web.dto.OrderPositionCreateDto;
import ua.levelup.web.dto.OrderViewDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger();

    private OrderDao orderDao = (OrderDao) DaoHolder
            .getDaoObject(DaoHolder.ORDER_DAO);
    private OrderPositionDao orderPositionDao = (OrderPositionDao) DaoHolder
            .getDaoObject(DaoHolder.ORDER_POSITION_DAO);

    @Override
    public OrderViewDto createOrder(OrderCreateDto orderCreateDto) {
        OrderValidator.validateNewOrder(orderCreateDto);
        Order order = OrderConverter.asOrder(orderCreateDto);
        order = orderDao.add(order);
        List<OrderPosition> orderPositionList = getOrderPositionList(orderCreateDto.getOrderPositionList());
        order.setOrderPositionList(orderPositionList);
        orderPositionDao.addAll(order);
        return OrderConverter.asOrderViewDto(order);
    }

    @Override
    public List<OrderViewDto> getUsersOrders(int userId) {
        List<Order> orderList = orderDao.getAllByUserId(userId);
        return getOrderViewDtoList(orderList);
    }

    @Override
    public List<OrderViewDto> getAllOrders() {
        List<Order> orderList = orderDao.getAll();
        return getOrderViewDtoList(orderList);
    }

    @Override
    public void updateOrder(OrderCreateDto orderCreateDto, int orderId) {
        OrderValidator.validateOrder(orderCreateDto);
        Order order = OrderConverter.asOrder(orderCreateDto);
        order.setId(orderId);
        orderDao.update(order);
    }

    private List<OrderPosition> getOrderPositionList(List<OrderPositionCreateDto> createDtoList) {
        List<OrderPosition> list = new ArrayList<>();
        for (OrderPositionCreateDto createDto : createDtoList) {
            list.add(OrderPositionConverter.asOrderPosition(createDto));
        }
        return list;
    }

    private List<OrderViewDto> getOrderViewDtoList(List<Order> orderList){
        List<OrderViewDto> orderViewDtoList = new ArrayList<>();
        for (Order order: orderList) {
            List<OrderPosition> orderPositionList = orderPositionDao.getAllByOrderId(order.getId());
            order.setOrderPositionList(orderPositionList);
            OrderViewDto viewDto = OrderConverter.asOrderViewDto(order);
            orderViewDtoList.add(viewDto);
        }
        return orderViewDtoList;
    }
}
