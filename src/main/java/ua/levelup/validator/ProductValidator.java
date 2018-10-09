//package ua.levelup.validator;
//
//import ua.levelup.exception.ValidationException;
//import ua.levelup.exception.support.MessageContainer;
//import ua.levelup.model.Product;
//import ua.levelup.web.servlet.support.SearchParams;
//import org.apache.commons.lang3.StringUtils;
//
//public enum ProductValidator {
//    ;
//
//    public static void validateProductSearchParams(SearchParams searchParams){
//        if(searchParams.getMinPrice() < 0 || searchParams.getMaxPrice() < 0){
//            throw new ValidationException(MessageContainer.getMessageProperties()
//                    .getProperty("PRICE_VALIDATION_ERROR"));
//        }
//        if(searchParams.getOrderMethod() == null){
//            throw new ValidationException(MessageContainer.getMessageProperties()
//                    .getProperty("ORDER_METHOD_VALIDATION_ERROR"));
//        }
//        if(searchParams.getProduct() == null){
//            throw new ValidationException(MessageContainer.getMessageProperties()
//                    .getProperty("PRODUCT_VALIDATION_ERROR"));
//        }
//    }
//
//    public static void validateNewProduct(Product product){
//        if(StringUtils.isEmpty(product.getName())){
//            throw new ValidationException(MessageContainer.getMessageProperties()
//                    .getProperty("EMPTY_PRODUCT_NAME"));
//        }
//        if(product.getCategory().getId() <= 0){
//            throw new ValidationException(MessageContainer.getMessageProperties()
//                    .getProperty("UNACCEPTABLE_CATEGORY_ID"));
//        }
//        if(product.getManufacturer().getId() <= 0){
//            throw new ValidationException(MessageContainer.getMessageProperties()
//                    .getProperty("UNACCEPTABLE_MANUFACTURER_ID"));
//        }
//        if(product.getPrice() <= 0){
//            throw new ValidationException(MessageContainer.getMessageProperties()
//                    .getProperty("UNACCEPTABLE_PRICE"));
//        }
//    }
//}
