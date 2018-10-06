package ua.levelup.converter.fromCreateDto;

import com.sun.istack.internal.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.create.OrderPositionCreateDto;

public class OrderPositionCreateDtoConverter
        implements Converter<OrderPositionCreateDto, OrderPosition> {

    @NotNull
    @Override
    public OrderPosition convert(@NonNull OrderPositionCreateDto orderPositionCreateDto) {
        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setProductId(orderPositionCreateDto.getProductId());
        orderPosition.setProductName(orderPositionCreateDto.getProductName());
        orderPosition.setQuantity(orderPositionCreateDto.getQuantity());
        orderPosition.setUnitPrice(orderPositionCreateDto.getUnitPrice());
        return orderPosition;
    }
}
