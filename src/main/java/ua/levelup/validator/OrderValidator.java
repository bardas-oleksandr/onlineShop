//package ua.levelup.validator;
//
//import ua.levelup.exception.ValidationException;
//import ua.levelup.exception.support.MessageContainer;
//import ua.levelup.model.Order;
//import org.apache.commons.lang3.StringUtils;
//
//public enum OrderValidator {
//    ;
//
//    public static void validateOrderWithOrderPositionList(Order order){
//        validateOrder(order);
//        if(order.getOrderPositionViewDtoList() == null || order.getOrderPositionViewDtoList().size() == 0){
//            throw new ValidationException(MessageContainer.getMessageProperties()
//                    .getProperty("NO_ORDER_POSITIONS"));
//        }
//    }
//
//    public static void validateOrder(Order order){
//        if(StringUtils.isEmpty(order.getAddress())){
//            throw new ValidationException(MessageContainer.getMessageProperties()
//                    .getProperty("EMPTY_ADDRESS"));
//        }
//    }
//
//    public static void validatePaymentConditions(int paymentConditionsIndex){
//        try{
//            Order.PaymentConditions.getPaymentConditions(paymentConditionsIndex);
//        }catch (IllegalArgumentException e){
//            throw new ValidationException(MessageContainer.getMessageProperties()
//                    .getProperty("UNACCEPTABLE_PAYMENT_CONDITIONS"));
//        }
//    }
//}