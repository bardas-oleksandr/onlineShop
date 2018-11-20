package ua.levelup.converter.fromdto;

import org.springframework.core.convert.converter.Converter;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.create.OrderPositionCreateDto;

/**
 *
 */
//Если пометить класс каждого конвертера как @Component, тогда Spring
//сам соберет все конвертеры в Set<Converter<?,?>> и вручную собирать уже не нужно
@Component("orderPositionCreateDtoConverter")
public class OrderPositionCreateDtoConverter
        implements Converter<OrderPositionCreateDto, OrderPosition> {

    @Override
    public OrderPosition convert(@NonNull OrderPositionCreateDto orderPositionCreateDto) {
        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setOrderId(orderPositionCreateDto.getOrderId());
        orderPosition.setProductId(orderPositionCreateDto.getProductId());
        orderPosition.setQuantity(orderPositionCreateDto.getQuantity());
        orderPosition.setUnitPrice(orderPositionCreateDto.getUnitPrice());
        return orderPosition;
    }
}