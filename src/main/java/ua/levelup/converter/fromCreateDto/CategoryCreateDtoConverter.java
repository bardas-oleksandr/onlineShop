package ua.levelup.converter.fromCreateDto;

import com.sun.istack.internal.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import ua.levelup.model.Category;
import ua.levelup.web.dto.create.CategoryCreateDto;

public class CategoryCreateDtoConverter implements Converter<CategoryCreateDto, Category> {

    @NotNull
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
