package ua.levelup.service.support;

import ua.levelup.service.*;
import ua.levelup.service.impl.*;

public enum ServiceHolder {
    USER_SERVICE, PRODUCT_SERVICE, CART_SERVICE, ORDER_SERVICE, CATEGORY_SERVICE, MANUFACTURER_SERVICE;

    private static UserService userService = new UserServiceImpl();
    private static ProductService productService = new ProductServiceImpl();
    private static CartService cartService = new CartServiceImpl();
    private static OrderService orderService = new OrderServiceImpl();
    private static CategoryService categoryService = new CategoryServiceImpl();
    private static ManufacturerService manufacturerService = new ManufacturerServiceImpl();

    public static Object getService(ServiceHolder factory) {
        switch (factory) {
            case USER_SERVICE:
                return userService;
            case PRODUCT_SERVICE:
                return productService;
            case CART_SERVICE:
                return cartService;
            case ORDER_SERVICE:
                return orderService;
            case CATEGORY_SERVICE:
                return categoryService;
            case MANUFACTURER_SERVICE:
                return manufacturerService;
            default:
                throw new IllegalStateException("Request for unsupported Service class");
        }
    }
}
