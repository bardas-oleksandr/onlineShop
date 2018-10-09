//package ua.levelup.mapper.support;
//
//import ua.levelup.mapper.*;
//
//public enum MapperHolder {
//    CATEGORY, MANUFACTURER, ORDER, ORDER_POSITION, PRODUCT, USER;
//
//    private static CategoryMapper categoryMapper = new CategoryMapper();
//    private static ManufacturerMapper manufacturerMapper = new ManufacturerMapper();
//    private static OrderMapper orderMapper = new OrderMapper();
//    private static OrderPositionMapper orderPositionMapper = new OrderPositionMapper();
//    private static ProductMapper productMapper = new ProductMapper();
//    private static UserMapper userMapper = new UserMapper();
//
//    public static AbstractMapper<?> getMapper(MapperHolder factory) {
//        switch (factory) {
//            case CATEGORY:
//                return categoryMapper;
//            case MANUFACTURER:
//                return manufacturerMapper;
//            case ORDER:
//                return orderMapper;
//            case ORDER_POSITION:
//                return orderPositionMapper;
//            case PRODUCT:
//                return productMapper;
//            case USER:
//                return userMapper;
//            default:
//                throw new IllegalStateException("Unsupported Mapper class");
//        }
//    }
//}
