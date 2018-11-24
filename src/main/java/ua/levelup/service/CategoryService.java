package ua.levelup.service;

import ua.levelup.web.dto.create.CategoryCreateDto;
import ua.levelup.web.dto.view.CategoryViewDto;

import java.util.List;

public interface CategoryService {
    CategoryViewDto create(CategoryCreateDto categoryCreateDto);

    CategoryViewDto update(CategoryCreateDto categoryCreateDto, int categoryId);

    void delete(int categoryId);

    CategoryViewDto get(int categoryId);

    List<CategoryViewDto> getCategoriesByLevel(int categoryLevel);

    List<CategoryViewDto> getCategoriesByParentId(int parentId);
}