package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.levelup.service.OrderService;
import ua.levelup.web.dto.create.OrderCreateDto;
import ua.levelup.web.dto.view.OrderViewDto;

import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    private static final String ID = "/{id}";
    private static final String ORDERS_PAGE = "orders";
    private static final String REDIRECT_ORDERS = "redirect:/order/";
    private static final String USER_ORDERS_PAGE = "userorders";
    private static final String ORDER_LIST_ATTRIBUTE = "orderList";

    @Autowired
    private OrderService orderService;

    @PostMapping
    public String createOrder(ModelMap modelMap) {

        return USER_ORDERS_PAGE;
    }

    @PostMapping(value = ID)
    public String modifyOrder(ModelMap modelMap, @PathVariable("id") int orderId
            , @ModelAttribute("orderCreateDto") OrderCreateDto orderCreateDto) {
        orderService.updateOrder(orderCreateDto, orderId);
        return REDIRECT_ORDERS;
    }

    @GetMapping
    public String ordersPage(ModelMap modelMap) {
        List<OrderViewDto> orderViewDtoList = orderService.getAllOrders();
        modelMap.addAttribute(ORDER_LIST_ATTRIBUTE, orderViewDtoList);
        return ORDERS_PAGE;
    }
}
