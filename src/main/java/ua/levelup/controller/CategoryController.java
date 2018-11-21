package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.service.CategoryService;
import ua.levelup.web.dto.create.CategoryCreateDto;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    private static final String REDIRECT_SUCCESS = "redirect:/success";
    private static final String REDIRECT_ROOT = "redirect:/";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping
    public String redirectRoot() {
        return REDIRECT_ROOT;
    }

    @PostMapping
    public String createCategory(@Valid @ModelAttribute CategoryCreateDto categoryCreateDto
            , BindingResult result, ModelMap modelMap) {

        if (result.hasErrors()) {
            return controllerUtils.redirectValidationError(result, modelMap);
        }
        categoryService.createCategory(categoryCreateDto);
        return REDIRECT_SUCCESS;
    }
}
