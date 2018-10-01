package ua.levelup.converter;

import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.OrderPositionDto;

public enum OrderPositionConverter {
    ;

    public static OrderPositionDto asOrderPositionDto(OrderPosition orderPosition){
        OrderPositionDto orderPositionDto = new OrderPositionDto();
        orderPositionDto.setProductId(orderPosition.getProductId());
        orderPositionDto.setProductName(orderPosition.getProductName());
        orderPositionDto.setQuantity(orderPosition.getQuantity());
        orderPositionDto.setUnitPrice(orderPosition.getUnitPrice());
        return orderPositionDto;
    }
}