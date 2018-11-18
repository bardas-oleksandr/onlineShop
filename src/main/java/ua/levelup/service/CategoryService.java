package ua.levelup.service;

import ua.levelup.web.dto.create.CategoryCreateDto;
import ua.levelup.web.dto.view.CategoryViewDto;

import javax.validation.Valid;
import java.util.List;

public interface CategoryService {
    void createNewCategory(@Valid CategoryCreateDto categoryCreateDto);
    List<CategoryViewDto> getCategoriesByLevel(int categoryLevel);
}
