package ua.levelup.converter.fromdto;

import org.springframework.core.convert.converter.Converter;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.create.OrderPositionCreateDto;

/**
 *
 */
@Component("orderPositionCreateDtoConverter")
public class OrderPositionCreateDtoConverter
        implements Converter<OrderPositionCreateDto, OrderPosition> {

    @Override
    public OrderPosition convert(@NonNull OrderPositionCreateDto orderPositionCreateDto) {
        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setOrderId(orderPositionCreateDto.getOrderId());
        orderPosition.setProductId(orderPositionCreateDto.getProductId());
        orderPosition.setProductName(orderPositionCreateDto.getProductName());
        orderPosition.setQuantity(orderPositionCreateDto.getQuantity());
        orderPosition.setUnitPrice(orderPositionCreateDto.getUnitPrice());
        return orderPosition;
    }
}