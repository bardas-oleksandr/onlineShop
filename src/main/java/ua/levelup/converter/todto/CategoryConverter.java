package ua.levelup.converter.todto;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.Category;
import ua.levelup.web.dto.view.CategoryViewDto;

/**
 *
 */
//Если пометить класс каждого конвертера как @Component, тогда Spring
//сам соберет все конвертеры в Set<Converter<?,?>> и вручную собирать уже не нужно
@Component("categoryConverter")
public class CategoryConverter implements Converter<Category, CategoryViewDto> {

    @Override
    public CategoryViewDto convert(@NonNull Category category) {
        CategoryViewDto categoryViewDto = new CategoryViewDto();
        categoryViewDto.setId(category.getId());
        categoryViewDto.setName(category.getName());
        if(category.getParentCategory() != null){
            CategoryViewDto parent = convert(category.getParentCategory());
            categoryViewDto.setParentCategoryViewDto(parent);
        }
        return categoryViewDto;
    }
}