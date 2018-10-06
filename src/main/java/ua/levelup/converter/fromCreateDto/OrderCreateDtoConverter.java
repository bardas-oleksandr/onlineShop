package ua.levelup.converter.fromCreateDto;

import com.sun.istack.internal.NotNull;
import org.springframework.core.convert.converter.Converter;
import ua.levelup.model.Order;
import ua.levelup.model.User;
import ua.levelup.web.dto.create.OrderCreateDto;

public class OrderCreateDtoConverter implements Converter<OrderCreateDto, Order> {

    @NotNull
    @Override
    public Order convert(@NotNull OrderCreateDto orderCreateDto) {
        Order order = new Order();
        User user = new User();
        user.setId(orderCreateDto.getUserId());
        order.setUser(user);
        order.setAddress(orderCreateDto.getAddress());
        order.setDate(orderCreateDto.getDate());
        order.setPaymentConditions(orderCreateDto.getPaymentConditionsIndex());
        return order;
    }
}
