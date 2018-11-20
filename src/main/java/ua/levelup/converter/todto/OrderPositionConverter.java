package ua.levelup.converter.todto;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.view.OrderPositionViewDto;

/**
 *
 */
//Если пометить класс каждого конвертера как @Component, тогда Spring
//сам соберет все конвертеры в Set<Converter<?,?>> и вручную собирать уже не нужно
@Component("orderPositionConverter")
public class OrderPositionConverter implements Converter<OrderPosition, OrderPositionViewDto> {

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