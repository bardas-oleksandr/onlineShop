package ua.levelup.converter;

import ua.levelup.model.Order;
import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.OrderCreateDto;
import ua.levelup.web.dto.OrderPositionCreateDto;
import ua.levelup.web.dto.OrderPositionViewDto;
import ua.levelup.web.dto.OrderViewDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public enum OrderConverter {
    ;

    private static final Logger logger = LogManager.getLogger();

    public static Order asOrder(OrderCreateDto createdOrder){
        Order order = new Order();
        order.setUserId(createdOrder.getUserId());
        order.setState(createdOrder.getState());
        order.setAddress(createdOrder.getAddress());
        order.setDate(createdOrder.getDate());
        order.setConditions(createdOrder.getConditions());
        order.setOrderPositionList(getOrderPositionList(createdOrder.getOrderPositionList()));
        return order;
    }

    public static OrderViewDto asOrderViewDto(Order order){
        OrderViewDto viewDto = new OrderViewDto();
        viewDto.setId(order.getId());
        viewDto.setState(order.getState().ordinal());
        viewDto.setUserId(order.getUserId());
        viewDto.setUserName(order.getUserName());
        viewDto.setDate(order.getDate());
        viewDto.setAddress(order.getAddress());
        viewDto.setConditions(order.getConditions().ordinal());
        viewDto.setOrderPositionList(getOrderPositionViewDtoList(order.getOrderPositionList()));
        return viewDto;
    }

    private static List<OrderPosition> getOrderPositionList(List<OrderPositionCreateDto> createDtoList){
        List<OrderPosition> list = new ArrayList<>();
        for (OrderPositionCreateDto createDto: createDtoList) {
            list.add(OrderPositionConverter.asOrderPosition(createDto));
        }
        return list;
    }

    private static List<OrderPositionViewDto> getOrderPositionViewDtoList(List<OrderPosition> orderPositionList){
        List<OrderPositionViewDto> list = new ArrayList<>();
        for (OrderPosition orderPosition: orderPositionList) {
            list.add(OrderPositionConverter.asOrderPositionViewDto(orderPosition));
        }
        return list;
    }
}