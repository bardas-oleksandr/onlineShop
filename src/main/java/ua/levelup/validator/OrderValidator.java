package ua.levelup.validator;

import ua.levelup.exception.ValidationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.support.PaymentConditions;
import ua.levelup.web.dto.OrderCreateDto;
import org.apache.commons.lang3.StringUtils;

public enum OrderValidator {
    ;

    public static void validateNewOrder(OrderCreateDto orderCreateDto){
        validateOrder(orderCreateDto);
        if(orderCreateDto.getOrderPositionList().size() == 0){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("NO_ORDER_POSITIONS"));
        }
    }

    public static void validateOrder(OrderCreateDto orderCreateDto){
        if(StringUtils.isEmpty(orderCreateDto.getAddress())){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("EMPTY_ADDRESS"));
        }
        try{
            PaymentConditions.getPaymentConditions(orderCreateDto.getConditions());
        }catch (IllegalArgumentException e){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("UNACCEPTABLE_PAYMENT_CONDITIONS"));
        }
    }
}