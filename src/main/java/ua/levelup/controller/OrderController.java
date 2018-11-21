package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.service.OrderService;
import ua.levelup.web.dto.create.OrderCreateDto;
import ua.levelup.web.dto.view.OrderViewDto;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    private static final String ID = "/{id}";
    private static final String ORDERS_PAGE = "orders";
    private static final String REDIRECT_ORDER = "redirect:/order/";
    private static final String ORDER_LIST_ATTRIBUTE = "orderList";

    @Autowired
    private OrderService orderService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping(value = ID)
    public String updateOrder(@Valid @ModelAttribute OrderCreateDto orderCreateDto
            , BindingResult result, ModelMap modelMap, @PathVariable("id") int orderId) {
        if (result.hasErrors()) {
            return controllerUtils.redirectValidationError(result, modelMap);
        }
        orderService.updateOrder(orderCreateDto, orderId);
        return REDIRECT_ORDER;
    }

    @GetMapping
    public String ordersPage(ModelMap modelMap) {
        List<OrderViewDto> orderViewDtoList = orderService.getAllOrders();
        modelMap.addAttribute(ORDER_LIST_ATTRIBUTE, orderViewDtoList);
        return ORDERS_PAGE;
    }
}