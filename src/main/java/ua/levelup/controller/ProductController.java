package ua.levelup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private static final String ID = "/{id}";
    private static final String DELETE = "/delete";
    private static final String DEFAULT_FILTER = "/default";
    private static final String REDIRECT_PRODUCT = "redirect:/product";
    private static final String SUCCESS_PAGE = "success";
    private static final String INDEX_PAGE = "index";

    @PostMapping
    public String createProduct(ModelMap modelMap){

        return SUCCESS_PAGE;
    }

    @GetMapping
    public String searchProduct(ModelMap modelMap){

        return INDEX_PAGE;
    }

    @GetMapping(value=DEFAULT_FILTER)
    public String defaultFilter(ModelMap modelMap){

        return REDIRECT_PRODUCT;
    }

    @PostMapping(value = ID)
    public String modifyProduct(ModelMap modelMap, @PathVariable("id") int productId){

        return INDEX_PAGE;
    }

    @PostMapping(value = DELETE + ID)
    public String deleteProduct(ModelMap modelMap, @PathVariable("id") int productId){

        return INDEX_PAGE;
    }
}
