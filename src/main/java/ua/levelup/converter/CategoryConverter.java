package ua.levelup.converter;

import ua.levelup.model.Category;
import ua.levelup.web.dto.CategoryDto;

public enum CategoryConverter {
    ;

    public static CategoryDto asCategoryDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        if(category.getParentCategory() != null){
            CategoryDto parent = CategoryConverter.asCategoryDto(category.getParentCategory());
            categoryDto.setParentCategoryDto(parent);
        }
        return categoryDto;
    }
}