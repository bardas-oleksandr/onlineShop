package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.controller.support.MessageResolver;
import ua.levelup.service.OrderService;
import ua.levelup.web.dto.create.OrderCreateDto;
import ua.levelup.web.dto.view.OrderViewDto;

import javax.validation.Valid;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {

    private static final String ID = "/{id}";
    private static final String REDIRECT_ORDER = "redirect:/order/";
    private static final String ORDERS_PAGE = "orders";
    private static final String ERROR_PAGE = "error";
    private static final String ORDER_LIST_ATTRIBUTE = "orderList";
    private static final String MESSAGE_CODE_ATTRIBUTE = "message_code";

    @Autowired
    private OrderService orderService;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private MessageResolver messageResolver;

    @PostMapping(value = ID)
    public String updateOrder(@Valid @ModelAttribute OrderCreateDto orderCreateDto
            , BindingResult result, ModelMap modelMap, @PathVariable("id") int orderId) {
        if (result.hasErrors()) {
            return controllerUtils.redirectValidationError(result, modelMap);
        }
        orderService.update(orderCreateDto, orderId);
        return REDIRECT_ORDER;
    }

    @GetMapping
    public String ordersPage(ModelMap modelMap) {
        List<OrderViewDto> orderViewDtoList = orderService.getAllOrders();
        modelMap.addAttribute(ORDER_LIST_ATTRIBUTE, orderViewDtoList);
        return ORDERS_PAGE;
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(Exception e) {
        String messageCode = messageResolver.resolveMessageForException(e);
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject(MESSAGE_CODE_ATTRIBUTE, messageCode);
        return modelAndView;
    }
}