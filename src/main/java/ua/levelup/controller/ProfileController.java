package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.levelup.service.OrderService;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.create.OrderCreateDto;
import ua.levelup.web.dto.view.CartViewDto;
import ua.levelup.web.dto.view.OrderViewDto;
import ua.levelup.web.dto.view.UserViewDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

    private static final String ID = "/{id}";
    private static final String ORDER = "/order";
    private static final String PROFILE_PAGE = "profile";
    private static final String USER_ORDERS_PAGE = "userorders";
    private static final String REDIRECT_PROFILE_ORDER = "redirect:/profile/order/";
    private static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
    private static final String USER_ATTRIBUTE = "user";
    private static final String CART_ATTRIBUTE = "cart";
    private static final String ORDER_LIST_ATTRIBUTE = "orderList";

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String profilePage(ModelMap modelMap, HttpServletRequest request) {
        SecurityContextImpl securityContext = (SecurityContextImpl) request
                .getSession(true).getAttribute(SPRING_SECURITY_CONTEXT);
        User user = (User) securityContext.getAuthentication().getPrincipal();
        String email = user.getUsername();

        UserViewDto viewDto = userService.getUserViewDto(email);
        modelMap.addAttribute(USER_ATTRIBUTE, viewDto);

        return PROFILE_PAGE;
    }

    @PostMapping(value = ORDER)
    public String createOrder(HttpServletRequest request
            , @ModelAttribute("order") OrderCreateDto orderCreateDto) {
        HttpSession session = request.getSession(true);
        CartViewDto cart = (CartViewDto) session.getAttribute(CART_ATTRIBUTE);
        orderService.createOrder(orderCreateDto, cart);
        cart.setProductInCartViewDtoList(new ArrayList<>());
        return REDIRECT_PROFILE_ORDER + orderCreateDto.getUserId();
    }

    @GetMapping(value = ORDER + ID)
    public String userOrdersPage(ModelMap modelMap, @PathVariable("id") int userId) {
        List<OrderViewDto> orderViewDtoList = orderService.getUsersOrders(userId);
        modelMap.addAttribute(ORDER_LIST_ATTRIBUTE, orderViewDtoList);
        return USER_ORDERS_PAGE;
    }
}
