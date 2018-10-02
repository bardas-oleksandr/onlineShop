package ua.levelup.converter;

import lombok.NonNull;
import ua.levelup.model.Order;
import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.OrderPositionDto;
import ua.levelup.web.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;

public enum OrderConverter {
    ;

    public static OrderDto asOrderDto(@NonNull Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserDto(UserConverter.asUserViewDto(order.getUser()));
        orderDto.setOrderState(order.getOrderState().ordinal());
        orderDto.setDate(order.getDate());
        orderDto.setAddress(order.getAddress());
        orderDto.setConditions(order.getPaymentConditions().ordinal());
        orderDto.setOrderPositionList(getOrderPositionDtoList(order.getOrderPositionList()));
        return orderDto;
    }

    private static List<OrderPositionDto> getOrderPositionDtoList(List<OrderPosition> orderPositionList){
        List<OrderPositionDto> list = new ArrayList<>();
        for (OrderPosition orderPosition: orderPositionList) {
            list.add(OrderPositionConverter.asOrderPositionDto(orderPosition));
        }
        return list;
    }
}