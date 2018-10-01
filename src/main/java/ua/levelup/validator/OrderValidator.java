package ua.levelup.validator;

import ua.levelup.exception.ValidationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.Order;
import ua.levelup.model.support.PaymentConditions;
import org.apache.commons.lang3.StringUtils;

public enum OrderValidator {
    ;

    public static void validateOrderWithOrderPositionList(Order order){
        validateOrder(order);
        if(order.getOrderPositionList() == null || order.getOrderPositionList().size() == 0){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("NO_ORDER_POSITIONS"));
        }
    }

    public static void validateOrder(Order order){
        if(StringUtils.isEmpty(order.getAddress())){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("EMPTY_ADDRESS"));
        }
    }

    public static void validatePaymentConditions(int paymentConditionsIndex){
        try{
            PaymentConditions.getPaymentConditions(paymentConditionsIndex);
        }catch (IllegalArgumentException e){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("UNACCEPTABLE_PAYMENT_CONDITIONS"));
        }
    }
}