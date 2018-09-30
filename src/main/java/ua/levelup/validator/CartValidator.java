package ua.levelup.validator;

import ua.levelup.exception.ValidationException;
import ua.levelup.exception.support.MessageHolder;

public enum CartValidator {
    ;

    public static void validateCartEntry(Integer productId, Integer count){
        if(productId == null || productId <= 0){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("CART_PRODUCT_VALIDATION_ERROR"));
        }
        if(count == null || count <= 0){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("CART_COUNT_VALIDATION_ERROR"));
        }
    }
}
