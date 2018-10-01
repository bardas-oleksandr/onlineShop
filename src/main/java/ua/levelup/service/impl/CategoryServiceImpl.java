package ua.levelup.service.impl;

import ua.levelup.converter.CategoryConverter;
import ua.levelup.dao.CategoryDao;
import ua.levelup.dao.support.DaoHolder;
import ua.levelup.model.Category;
import ua.levelup.service.CategoryService;
import ua.levelup.validator.CategoryValidator;
import ua.levelup.web.dto.create.CategoryCreateDto;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = (CategoryDao) DaoHolder.getDaoObject(DaoHolder.CATEGORY_DAO);

    @Override
    public void createNewCategory(CategoryCreateDto categoryCreateDto) {
        CategoryValidator.validateNewCategory(categoryCreateDto);
        Category category = CategoryConverter.asCategory(categoryCreateDto);
        categoryDao.add(category);
    }
}
