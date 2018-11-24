package ua.levelup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ua.levelup.dao.CategoryDao;
import ua.levelup.model.Category;
import ua.levelup.service.CategoryService;
import ua.levelup.web.dto.create.CategoryCreateDto;
import ua.levelup.web.dto.view.CategoryViewDto;

import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ConversionService conversionService;

    @Override
    public CategoryViewDto create(CategoryCreateDto categoryCreateDto) {
        Category category = conversionService.convert(categoryCreateDto, Category.class);
        categoryDao.add(category);
        return conversionService.convert(category, CategoryViewDto.class);
    }

    @Override
    public CategoryViewDto update(CategoryCreateDto categoryCreateDto, int categoryId) {
        Category category = conversionService.convert(categoryCreateDto, Category.class);
        category.setId(categoryId);
        categoryDao.update(category);
        return conversionService.convert(category, CategoryViewDto.class);
    }

    @Override
    public void delete(int categoryId) {
        categoryDao.delete(categoryId);
    }

    @Override
    public CategoryViewDto get(int categoryId) {
        Category category = categoryDao.getById(categoryId);
        return conversionService.convert(category, CategoryViewDto.class);
    }

    @Override
    public List<CategoryViewDto> getCategoriesByLevel(int categoryLevel) {
        List<Category> categories = categoryDao.getAllByLevel(categoryLevel);
        List<CategoryViewDto> viewDtos = new ArrayList<>();
        categories.stream().forEach((item) -> viewDtos
                .add(conversionService.convert(item, CategoryViewDto.class)));
        return viewDtos;
    }

    @Override
    public List<CategoryViewDto> getCategoriesByParentId(int parentId) {
        List<Category> categories = categoryDao.getAllByParentId(parentId);
        List<CategoryViewDto> viewDtos = new ArrayList<>();
        categories.stream().forEach((item) -> viewDtos
                .add(conversionService.convert(item, CategoryViewDto.class)));
        return viewDtos;
    }
}