package ua.levelup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

    private static final String ID = "/{id}";
    private static final String SUCCESS_PAGE = "success";
    private static final String CART_PAGE = "cart";

    @PostMapping(value = ID)
    public String putIntoCart(ModelMap modelMap, @PathVariable("id") int productId) {

        return SUCCESS_PAGE;
    }

    @GetMapping
    public String cartViewPage(ModelMap modelMap) {

        return CART_PAGE;
    }
}
