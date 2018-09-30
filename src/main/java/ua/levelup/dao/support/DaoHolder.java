package ua.levelup.dao.support;

import ua.levelup.dao.*;
import ua.levelup.dao.impl.*;

public enum DaoHolder {
    CATEGORY_DAO,MANUFACTURER_DAO,ORDER_DAO,ORDER_POSITION_DAO,PRODUCT_DAO,USER_DAO;

    private static CategoryDao categoryDao = new CategoryDaoImpl();
    private static ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
    private static OrderDao orderDao = new OrderDaoImpl();
    private static OrderPositionDao orderPositionDao = new OrderPositionDaoImpl();
    private static ProductDao productDao = new ProductDaoImpl();
    private static UserDao userDao = new UserDaoImpl();

    public static Object getDaoObject(DaoHolder daoType){
        switch (daoType){
            case CATEGORY_DAO: return categoryDao;
            case MANUFACTURER_DAO: return manufacturerDao;
            case ORDER_DAO: return orderDao;
            case ORDER_POSITION_DAO: return orderPositionDao;
            case PRODUCT_DAO: return productDao;
            case USER_DAO: return userDao;
            default: throw new IllegalStateException("Request for unsupported Dao class");
        }
    }
}
