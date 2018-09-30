package ua.levelup.service;

import ua.levelup.web.dto.CategoryCreateDto;

public interface CategoryService {
    void createNewCategory(CategoryCreateDto categoryCreateDto);
}
