//package ua.levelup.service.impl;
//
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.stereotype.Service;
//import ua.levelup.dao.OrderDao;
//import ua.levelup.dao.OrderPositionDao;
//import ua.levelup.model.Order;
//import ua.levelup.model.OrderPosition;
//import ua.levelup.service.OrderService;
//import ua.levelup.validator.OrderValidator;
//import ua.levelup.web.dto.view.OrderViewDto;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Setter
//@Service("orderService")
//public class OrderServiceImpl implements OrderService {
//
//    private static final Logger logger = LogManager.getLogger();
//
//    @Autowired
//    private OrderDao orderDao;
//
//    @Autowired
//    private OrderPositionDao orderPositionDao;
//
//    @Autowired
//    ConversionService conversionService;
//
//    @Override
//    public OrderViewDto createOrder(Order order) {
//        OrderValidator.validateOrderWithOrderPositionList(order);
//        order = orderDao.add(order);
//        orderPositionDao.addAll(order.getOrderPositionList());
//        return conversionService.convert(order, OrderViewDto.class);
//    }
//
//    @Override
//    public List<OrderViewDto> getUsersOrders(int userId) {
//        List<Order> orderList = orderDao.getAllByUserId(userId);
//        return getOrderDtoList(orderList);
//    }
//
//    @Override
//    public List<OrderViewDto> getAllOrders() {
//        List<Order> orderList = orderDao.getAll();
//        return getOrderDtoList(orderList);
//    }
//
//    @Override
//    public void updateOrder(Order order, int orderId) {
//        OrderValidator.validateOrder(order);
//        order.setId(orderId);
//        orderDao.update(order);
//    }
//
//    private List<OrderViewDto> getOrderDtoList(List<Order> orderList){
//        List<OrderViewDto> orderDtoList = new ArrayList<>();
//        for (Order order: orderList) {
//            List<OrderPosition> orderPositionList = orderPositionDao.getAllByOrderId(order.getId());
//            order.setOrderPositionList(orderPositionList);
//            OrderViewDto orderDto = conversionService.convert(order,OrderViewDto.class);
//            orderDtoList.add(orderDto);
//        }
//        return orderDtoList;
//    }
//}