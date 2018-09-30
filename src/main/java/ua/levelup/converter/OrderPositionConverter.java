package ua.levelup.converter;

import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.OrderPositionCreateDto;
import ua.levelup.web.dto.OrderPositionViewDto;

public enum OrderPositionConverter {
    ;

    public static OrderPosition asOrderPosition(OrderPositionCreateDto createdOrderPosition){
        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setProductId(createdOrderPosition.getProductId());
        orderPosition.setQuantity(createdOrderPosition.getQuantity());
        orderPosition.setUnitPrice(createdOrderPosition.getUnitPrice());
        return orderPosition;
    }

    public static OrderPositionViewDto asOrderPositionViewDto(OrderPosition orderPosition){
        OrderPositionViewDto viewDto = new OrderPositionViewDto();
        viewDto.setProductId(orderPosition.getProductId());
        viewDto.setProductName(orderPosition.getProductName());
        viewDto.setQuantity(orderPosition.getQuantity());
        viewDto.setUnitPrice(orderPosition.getUnitPrice());
        return viewDto;
    }
}