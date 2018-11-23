package ua.levelup.service;

import ua.levelup.web.dto.create.CategoryCreateDto;
import ua.levelup.web.dto.view.CategoryViewDto;

import java.util.List;

public interface CategoryService {
    CategoryViewDto createCategory(CategoryCreateDto categoryCreateDto);

    List<CategoryViewDto> getCategoriesByLevel(int categoryLevel);
}