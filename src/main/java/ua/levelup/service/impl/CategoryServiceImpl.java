//package ua.levelup.service.impl;
//
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ua.levelup.dao.CategoryDao;
//import ua.levelup.model.Category;
//import ua.levelup.service.CategoryService;
//import ua.levelup.validator.CategoryValidator;
//
//@Setter
//@Service("categoryService")
//public class CategoryServiceImpl implements CategoryService {
//
//    @Autowired
//    private CategoryDao categoryDao;
//
//    @Override
//    public void createNewCategory(Category category) {
//        CategoryValidator.validateNewCategory(category);
//        categoryDao.add(category);
//    }
//}