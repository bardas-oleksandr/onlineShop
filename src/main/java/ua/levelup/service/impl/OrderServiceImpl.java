package ua.levelup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ua.levelup.dao.OrderDao;
import ua.levelup.dao.OrderPositionDao;
import ua.levelup.dao.UserDao;
import ua.levelup.model.Order;
import ua.levelup.model.OrderPosition;
import ua.levelup.model.User;
import ua.levelup.service.OrderService;
import ua.levelup.web.dto.create.OrderCreateDto;
import ua.levelup.web.dto.view.CartViewDto;
import ua.levelup.web.dto.view.OrderViewDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.levelup.web.dto.view.ProductInCartViewDto;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderPositionDao orderPositionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ConversionService conversionService;

    @Override
    public OrderViewDto createOrder(@Valid OrderCreateDto orderCreateDto, CartViewDto cartViewDto) {
        Order order = conversionService.convert(orderCreateDto, Order.class);
        User user = userDao.getById(order.getUser().getId());
        order.setUser(user);
        order.setDate(new Timestamp(System.currentTimeMillis()));
        orderDao.add(order);

        List<ProductInCartViewDto> productInCartViewDtoList = cartViewDto.getProductInCartViewDtoList();
        List<OrderPosition> orderPositionList = new ArrayList<>();
        productInCartViewDtoList.stream().forEach((productInCart) -> orderPositionList
                .add(conversionService.convert(productInCart, OrderPosition.class)));
        orderPositionList.stream().forEach((orderPosition) -> orderPosition.setOrderId(order.getId()));
        orderPositionDao.addAll(orderPositionList);

        order.setOrderPositionList(orderPositionList);
        return conversionService.convert(order, OrderViewDto.class);
    }

    @Override
    public OrderViewDto updateOrder(@Valid OrderCreateDto orderCreateDto, int orderId) {
        Order order = conversionService.convert(orderCreateDto, Order.class);
        order.setId(orderId);
        orderDao.update(order);
        return conversionService.convert(order, OrderViewDto.class);
    }

    @Override
    public List<OrderViewDto> getUsersOrders(int userId) {
        return getOrderViewDtoList(orderDao.getAllByUserId(userId));
    }

    @Override
    public List<OrderViewDto> getAllOrders() {
        return getOrderViewDtoList(orderDao.getAll());
    }

    private List<OrderViewDto> getOrderViewDtoList(List<Order> orderList) {
        List<OrderViewDto> viewDtos = new ArrayList<>();
        orderList.stream().forEach((order) -> viewDtos
                .add(conversionService.convert(order, OrderViewDto.class)));
        return viewDtos;
    }
}