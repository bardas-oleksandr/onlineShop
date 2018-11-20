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
//Если пометить класс каждого конвертера как @Component, тогда Spring
//сам соберет все конвертеры в Set<Converter<?,?>> и вручную собирать уже не нужно
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
        order.setPayed(orderCreateDto.isPayed());
        order.setOrderState(Order.OrderState
                .get(orderCreateDto.getOrderStateIndex()));
        order.setPaymentConditions(Order.PaymentConditions.get(orderCreateDto
                .getPaymentConditionsIndex()));
        return order;
    }
}