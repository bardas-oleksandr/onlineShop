package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.levelup.service.CategoryService;
import ua.levelup.web.dto.create.CategoryCreateDto;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    private static final String SUCCESS_PAGE = "success";

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public String createCategory(@ModelAttribute CategoryCreateDto categoryCreateDto){
        categoryService.createNewCategory(categoryCreateDto);
        return SUCCESS_PAGE;
    }
}
