package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.controller.support.MessageResolver;
import ua.levelup.model.Order;
import ua.levelup.service.OrderService;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.create.OrderCreateDto;
import ua.levelup.web.dto.view.CartViewDto;
import ua.levelup.web.dto.view.OrderViewDto;
import ua.levelup.web.dto.view.UserViewDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

    private static final String ID = "/{id}";
    private static final String ORDER = "/order";
    private static final String PROFILE_PAGE = "profile";
    private static final String USER_ORDERS_PAGE = "userorders";
    private static final String VALIDATION_ERROR_PAGE = "validationerror";
    private static final String ERROR_PAGE = "error";
    private static final String REDIRECT_PROFILE_ORDER = "redirect:/profile/order/";
    private static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
    private static final String ID_ATTRIBUTE = "id";
    private static final String USER_ATTRIBUTE = "user";
    private static final String CART_ATTRIBUTE = "cart";
    private static final String ORDER_LIST_ATTRIBUTE = "orderList";
    private static final String MESSAGE_CODES_ATTRIBUTE = "messageCodes";
    private static final String MESSAGE_CODE_ATTRIBUTE = "message_code";

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private MessageResolver messageResolver;

    @Autowired
    private Validator validator;

    @PostMapping(value = ORDER)
    public String createOrder(@ModelAttribute OrderCreateDto orderCreateDto
            , HttpServletRequest request, ModelMap modelMap) {

        orderCreateDto.setDate(new Timestamp(System.currentTimeMillis()));
        orderCreateDto.setPayed(false);
        orderCreateDto.setOrderStateIndex(Order.OrderState.REGISTERED.ordinal());

        Set<ConstraintViolation<OrderCreateDto>> violations = validator.validate(orderCreateDto);
        if(violations.size() > 0){
            List<String> messageCodes = new ArrayList<>();
            violations.stream().forEach((violation) -> messageCodes
                    .add(violation.getMessage()));
            modelMap.addAttribute(MESSAGE_CODES_ATTRIBUTE, messageCodes);
            return VALIDATION_ERROR_PAGE;
        }

        HttpSession session = request.getSession(true);
        CartViewDto cart = (CartViewDto) session.getAttribute(CART_ATTRIBUTE);
        orderService.create(orderCreateDto, cart);
        return REDIRECT_PROFILE_ORDER + orderCreateDto.getUserId();
    }

    @GetMapping
    public String profilePage(ModelMap modelMap, HttpServletRequest request) {
        SecurityContextImpl securityContext = (SecurityContextImpl) request
                .getSession(true).getAttribute(SPRING_SECURITY_CONTEXT);
        User user = (User) securityContext.getAuthentication().getPrincipal();
        String email = user.getUsername();
        UserViewDto viewDto = userService.getUserViewDtoByEmail(email);
        modelMap.addAttribute(USER_ATTRIBUTE, viewDto);
        return PROFILE_PAGE;
    }

    @GetMapping(value = ORDER)
    public String userOrdersPage(){
        return PROFILE_PAGE;
    }

    @GetMapping(value = ORDER + ID)
    public String userOrdersPage(HttpServletRequest request, ModelMap modelMap
            , @PathVariable(ID_ATTRIBUTE) int userId) {
        List<OrderViewDto> orderViewDtoList = orderService.getUsersOrders(userId);
        modelMap.addAttribute(ORDER_LIST_ATTRIBUTE, orderViewDtoList);

        SecurityContextImpl securityContext = (SecurityContextImpl) request
                .getSession(true).getAttribute(SPRING_SECURITY_CONTEXT);
        if (securityContext != null) {
            User user = (User) securityContext.getAuthentication().getPrincipal();
            String email = user.getUsername();
            UserViewDto viewDto = userService.getUserViewDtoByEmail(email);
            modelMap.addAttribute(USER_ATTRIBUTE, viewDto);
        }
        return USER_ORDERS_PAGE;
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(Exception e) {
        String messageCode = messageResolver.resolveMessageForException(e);
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject(MESSAGE_CODE_ATTRIBUTE, messageCode);
        return modelAndView;
    }
}