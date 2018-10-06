package ua.levelup.converter.toViewDto;

import com.sun.istack.internal.NotNull;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.view.OrderPositionViewDto;

@Component("orderPositionConverter")
public class OrderPositionConverter implements Converter<OrderPosition, OrderPositionViewDto> {

    @NotNull
    @Override
    public OrderPositionViewDto convert(@NonNull OrderPosition orderPosition) {
        OrderPositionViewDto orderPositionViewDto = new OrderPositionViewDto();
        orderPositionViewDto.setOrderId(orderPosition.getOrderId());
        orderPositionViewDto.setProductId(orderPosition.getProductId());
        orderPositionViewDto.setProductName(orderPosition.getProductName());
        orderPositionViewDto.setQuantity(orderPosition.getQuantity());
        orderPositionViewDto.setUnitPrice(orderPosition.getUnitPrice());
        return orderPositionViewDto;
    }
}