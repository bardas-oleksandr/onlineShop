package ua.levelup.converter.toViewDto;

import com.sun.istack.internal.NotNull;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.Category;
import ua.levelup.web.dto.view.CategoryViewDto;

@Component("categoryConverter")
public class CategoryConverter implements Converter<Category, CategoryViewDto> {

    @NotNull
    @Override
    public CategoryViewDto convert(@NonNull Category category) {
        CategoryViewDto categoryViewDto = new CategoryViewDto();
        categoryViewDto.setId(category.getId());
        categoryViewDto.setName(category.getName());
        if(category.getParentCategory() != null){
            CategoryViewDto parent = convert(category.getParentCategory());
            categoryViewDto.setParentCategoryDto(parent);
        }
        return categoryViewDto;
    }
}