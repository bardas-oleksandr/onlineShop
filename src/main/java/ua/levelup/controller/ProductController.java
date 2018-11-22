package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.service.ProductService;
import ua.levelup.web.dto.create.ProductCreateDto;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private static final String ID = "/{id}";
    private static final String DELETE = "/delete";
    private static final String REDIRECT_SEARCH = "redirect:/search/";
    private static final String REDIRECT_SUCCESS = "redirect:/success";
    private static final String ID_ATTRIBUTE = "id";

    @Autowired
    private ProductService productService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping
    public String createProduct(@Valid @ModelAttribute ProductCreateDto productCreateDto
            , BindingResult result, ModelMap modelMap) {

        if (result.hasErrors()) {
            return controllerUtils.redirectValidationError(result, modelMap);
        }
        productService.createProduct(productCreateDto);
        return REDIRECT_SUCCESS;
    }

    @PostMapping(value = ID)
    public String updateProduct(ModelMap modelMap, @PathVariable(ID_ATTRIBUTE) int productId
            , @Valid @ModelAttribute ProductCreateDto productCreateDto, BindingResult result) {
        if (result.hasErrors()) {
            return controllerUtils.redirectValidationError(result, modelMap);
        }
        productService.updateProduct(productCreateDto, productId);
        return REDIRECT_SEARCH;
    }

    @PostMapping(value = DELETE + ID)
    public String deleteProduct(@PathVariable(ID_ATTRIBUTE) int productId) {
        productService.deleteProduct(productId);
        return REDIRECT_SEARCH;
    }

    @GetMapping
    public String getProductRedirectingSearch() {
        return REDIRECT_SEARCH;
    }

    @GetMapping(value = ID)
    public String getProductIdRedirectingSearch() {
        return REDIRECT_SEARCH;
    }
}