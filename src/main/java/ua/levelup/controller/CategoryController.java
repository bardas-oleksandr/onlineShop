package ua.levelup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    private static final String ID = "/{id}";
    private static final String DELETE = "/delete";
    private static final String SUCCESS_PAGE = "categoryCreated";
    private static final String CATEGORY_PAGE = "category";

    @PostMapping
    public String createCategory(ModelMap modelMap){

        return SUCCESS_PAGE;
    }

    @GetMapping
    public String getCategories(ModelMap modelMap){

        return CATEGORY_PAGE;
    }

    @PostMapping(value = ID)
    public String modifyCategory(ModelMap modelMap, @PathVariable("id") int categoryId){

        return CATEGORY_PAGE;
    }

    @PostMapping(value = DELETE + ID)
    public String deleteCategory(ModelMap modelMap, @PathVariable("id") int categoryId){

        return CATEGORY_PAGE;
    }
}
