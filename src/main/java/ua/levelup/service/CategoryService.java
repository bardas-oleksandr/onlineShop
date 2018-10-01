package ua.levelup.service;

import ua.levelup.web.dto.create.CategoryCreateDto;

public interface CategoryService {
    void createNewCategory(CategoryCreateDto categoryCreateDto);
}
