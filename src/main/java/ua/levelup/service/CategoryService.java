package ua.levelup.service;

import ua.levelup.web.dto.create.CategoryCreateDto;
import ua.levelup.web.dto.view.CategoryViewDto;

import javax.validation.Valid;
import java.util.List;

public interface CategoryService {
    CategoryViewDto createCategory(@Valid CategoryCreateDto categoryCreateDto);
    List<CategoryViewDto> getCategoriesByLevel(int categoryLevel);
}