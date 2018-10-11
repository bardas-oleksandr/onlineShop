package ua.levelup.converter.fromdto;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.Order;
import ua.levelup.model.User;
import ua.levelup.web.dto.create.OrderCreateDto;

/**
 *
 */
@Component("orderCreateDtoConverter")
public class OrderCreateDtoConverter implements Converter<OrderCreateDto, Order> {

    @Override
    public Order convert(@NonNull OrderCreateDto orderCreateDto) {
        Order order = new Order();
        User user = new User();
        user.setId(orderCreateDto.getUserId());
        order.setUser(user);
        order.setAddress(orderCreateDto.getAddress());
        order.setDate(orderCreateDto.getDate());
        order.setPaymentConditions(Order.PaymentConditions.get(orderCreateDto
                .getPaymentConditionsIndex()));
        return order;
    }
}