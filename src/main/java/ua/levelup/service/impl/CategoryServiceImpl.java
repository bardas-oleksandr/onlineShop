package ua.levelup.service.impl;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ua.levelup.dao.CategoryDao;
import ua.levelup.model.Category;
import ua.levelup.service.CategoryService;
import ua.levelup.web.dto.create.CategoryCreateDto;
import ua.levelup.web.dto.view.CategoryViewDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Setter
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ConversionService conversionService;

    @Override
    public CategoryViewDto createNewCategory(@Valid CategoryCreateDto categoryCreateDto) {
        Category category = conversionService.convert(categoryCreateDto, Category.class);

        System.out.println("Parent category: " + category.getParentCategory());
        System.out.println("Category name: " + category.getName());

        categoryDao.add(category);
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
}