package ua.levelup.dao.support;

public enum OrderMethod {
    CHEAP_FIRST, CHEAP_LAST, PRODUCT_NAME;

    public static OrderMethod getOrderMethod(int orderMethod) {
        switch (orderMethod) {
            case 0:
                return CHEAP_FIRST;
            case 1:
                return CHEAP_LAST;
            case 2:
                return PRODUCT_NAME;
            default:
                throw new IllegalArgumentException("Illegal order method: " + orderMethod);
        }
    }
}
