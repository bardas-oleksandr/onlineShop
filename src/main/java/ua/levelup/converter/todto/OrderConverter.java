package ua.levelup.converter.todto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.Order;
import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.view.OrderPositionViewDto;
import ua.levelup.web.dto.view.OrderViewDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Component("orderConverter")
public class OrderConverter  implements Converter<Order, OrderViewDto> {

    @Autowired
    UserConverter userConverter;

    @Autowired
    OrderPositionConverter orderPositionConverter;

    @Override
    public OrderViewDto convert(@NonNull Order order) {
        OrderViewDto orderViewDto = new OrderViewDto();
        orderViewDto.setId(order.getId());
        orderViewDto.setUserDto(userConverter.convert(order.getUser()));
        orderViewDto.setAddress(order.getAddress());
        orderViewDto.setDate(order.getDate());
        orderViewDto.setPayed(order.isPayed());
        orderViewDto.setOrderState(order.getOrderState().ordinal());
        orderViewDto.setConditions(order.getPaymentConditions().ordinal());
        orderViewDto.setOrderPositionViewDtoList(convertList(order.getOrderPositionList()));
        return orderViewDto;
    }

    private List<OrderPositionViewDto> convertList(List<OrderPosition> orderPositionList){
        List<OrderPositionViewDto> result = new ArrayList<>();
        orderPositionList.forEach((orderPosition)->result
                .add(orderPositionConverter.convert(orderPosition)));
        return result;
    }
}