package ua.levelup.validator;

import ua.levelup.exception.ValidationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.web.servlet.support.SearchParams;
import ua.levelup.web.dto.ProductCreateDto;
import org.apache.commons.lang3.StringUtils;

public enum ProductValidator {
    ;

    public static void validateSearchQuery(SearchParams searchQuery){
        if(searchQuery.getMinPrice() < 0 || searchQuery.getMaxPrice() < 0){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("PRICE_VALIDATION_ERROR"));
        }
        if(searchQuery.getOrderMethod() == null){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("ORDER_METHOD_VALIDATION_ERROR"));
        }
        if(searchQuery.getProduct() == null){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("PRODUCT_VALIDATION_ERROR"));
        }
    }

    public static void validateNewProduct(ProductCreateDto productCreateDto){
        if(StringUtils.isEmpty(productCreateDto.getName())){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("EMPTY_PRODUCT_NAME"));
        }
        if(productCreateDto.getCategoryId() <= 0){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("UNACCEPTABLE_CATEGORY_ID"));
        }
        if(productCreateDto.getManufacturerId() <= 0){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("UNACCEPTABLE_MANUFACTURER_ID"));
        }
        if(productCreateDto.getPrice() <= 0){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("UNACCEPTABLE_PRICE"));
        }
    }
}
