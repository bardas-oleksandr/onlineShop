package ua.levelup.converter.fromdto;

import org.springframework.core.convert.converter.Converter;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.levelup.model.Category;
import ua.levelup.web.dto.create.CategoryCreateDto;

/**
 *
 */
@Component("categoryCreateDtoConverter")
public class CategoryCreateDtoConverter implements Converter<CategoryCreateDto, Category> {

    @Override
    public Category convert(@NonNull CategoryCreateDto categoryCreateDto) {
        Category category = new Category();
        category.setName(categoryCreateDto.getName());
        Category parentCategory = new Category();
        parentCategory.setId(categoryCreateDto.getParentCategoryId());
        category.setParentCategory(parentCategory);
        return category;
    }
}