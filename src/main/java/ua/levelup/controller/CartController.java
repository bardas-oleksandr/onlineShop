package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.service.CartService;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.create.ProductInCartCreateDto;
import ua.levelup.web.dto.view.CartViewDto;
import ua.levelup.web.dto.view.UserViewDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

    private static final String ID = "/{id}";
    private static final String REDIRECT_SUCCESS = "redirect:/success";
    private static final String REDIRECT_CART = "redirect:/cart";
    private static final String CART_PAGE = "cart";
    private static final String ID_ATTRIBUTE = "id";
    private static final String CART_ATTRIBUTE = "cart";
    private static final String USER_ATTRIBUTE = "user";
    private static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping
    public String flushCart(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        CartViewDto cart = (CartViewDto) session.getAttribute(CART_ATTRIBUTE);
        if (cart != null) {
            cart.setProductInCartViewDtoList(new ArrayList<>());
        }
        return REDIRECT_CART;
    }

    //Порядок объявления параметров метода имеет значение. Параметр BindingResult result
    //должен быть объявлен сразу после параметра, помеченного @Valid @ModelAttribute
    @PostMapping(value = ID)
    public String putIntoCart(@PathVariable(ID_ATTRIBUTE) int productId
            , @Valid @ModelAttribute ProductInCartCreateDto productInCartCreateDto
            , BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        if (result.hasErrors()) {
            return controllerUtils.redirectValidationError(result, modelMap);
        }
        HttpSession session = request.getSession(true);
        CartViewDto cart = (CartViewDto) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new CartViewDto();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        cartService.putIntoCart(cart, productInCartCreateDto);
        return REDIRECT_SUCCESS;
    }

    @GetMapping
    public String cartViewPage(HttpServletRequest request, ModelMap modelMap) {

        SecurityContextImpl securityContext = (SecurityContextImpl) request
                .getSession(true).getAttribute(SPRING_SECURITY_CONTEXT);
        if (securityContext != null) {
            User user = (User) securityContext.getAuthentication().getPrincipal();
            String email = user.getUsername();
            UserViewDto viewDto = userService.getUserViewDto(email);
            modelMap.addAttribute(USER_ATTRIBUTE, viewDto);
        }
        return CART_PAGE;
    }
}