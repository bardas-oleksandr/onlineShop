package ua.levelup.service.support;

import ua.levelup.dao.CategoryDao;
import ua.levelup.dao.ManufacturerDao;
import ua.levelup.dao.support.DaoHolder;
import ua.levelup.model.Category;
import ua.levelup.model.Manufacturer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public enum SearchUtils {
    ;

    private static final Logger logger = LogManager.getLogger();

    private static CategoryDao categoryDao = (CategoryDao) DaoHolder.getDaoObject(DaoHolder.CATEGORY_DAO);
    private static ManufacturerDao manufacturerDao = (ManufacturerDao) DaoHolder
            .getDaoObject(DaoHolder.MANUFACTURER_DAO);

    public static void initSearchingModel(HttpServletRequest request){
        List<Category> categoryList = categoryDao.getAllByLevel(0);
        List<Category> subcategoryList = categoryDao.getAllByLevel(1);
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        request.setAttribute("categoryList",categoryList);
        request.setAttribute("subcategoryList",subcategoryList);
        request.setAttribute("manufacturerList",manufacturerList);
    }
}