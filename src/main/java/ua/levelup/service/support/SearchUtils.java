package ua.levelup.service.support;

import org.springframework.beans.factory.annotation.Autowired;
import ua.levelup.dao.CategoryDao;
import ua.levelup.dao.ManufacturerDao;
import ua.levelup.model.Category;
import ua.levelup.model.Manufacturer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SearchUtils {

    @Autowired
    private static CategoryDao categoryDao;

    @Autowired
    private static ManufacturerDao manufacturerDao;

    public static void initSearchingModel(HttpServletRequest request){
        List<Category> categoryList = categoryDao.getAllByLevel(0);
        List<Category> subcategoryList = categoryDao.getAllByLevel(1);
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        request.setAttribute("categoryList",categoryList);
        request.setAttribute("subcategoryList",subcategoryList);
        request.setAttribute("manufacturerList",manufacturerList);
    }
}