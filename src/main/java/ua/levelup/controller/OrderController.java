package ua.levelup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/order")
public class OrderController {

    private static final String ID = "/{id}";
    private static final String ORDERS_PAGE = "orders";
    private static final String USER_ORDERS_PAGE = "userorders";

    @PostMapping
    public String createOrder(ModelMap modelMap) {

        return USER_ORDERS_PAGE;
    }

    @PostMapping(value = ID)
    public String modifyOrder(ModelMap modelMap, @PathVariable("id") int orderId) {

        return ORDERS_PAGE;
    }
}
