package ua.levelup.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.levelup.dao.CategoryDao;
import ua.levelup.model.Category;
import ua.levelup.service.CategoryService;
import ua.levelup.validator.CategoryValidator;

@Service("categoryService")
@Getter
@Setter
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public void createNewCategory(Category category) {
        CategoryValidator.validateNewCategory(category);
        categoryDao.add(category);
    }
}
