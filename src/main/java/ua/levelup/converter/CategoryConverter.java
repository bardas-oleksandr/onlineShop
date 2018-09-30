package ua.levelup.converter;

import ua.levelup.model.Category;
import ua.levelup.web.dto.CategoryCreateDto;
import ua.levelup.web.dto.CategoryViewDto;

public enum CategoryConverter {
    ;

    public static Category asCategory(CategoryCreateDto createdCategory){
        Category category = new Category();
        category.setName(createdCategory.getName());
        category.setParentId(createdCategory.getParentId());
        return category;
    }

    public static CategoryViewDto asCategoryViewDto(Category category){
        CategoryViewDto viewDto = new CategoryViewDto();
        viewDto.setId(category.getId());
        viewDto.setName(category.getName());
        viewDto.setParentId(category.getParentId());
        return viewDto;
    }
}