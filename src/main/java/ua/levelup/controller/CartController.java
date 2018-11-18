package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.levelup.model.Cart;
import ua.levelup.service.CartService;
import ua.levelup.web.dto.view.CartViewDto;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

    private static final String ID = "/{id}";
    private static final String SUCCESS_PAGE = "success";
    private static final String CART_PAGE = "cart";
    private static final String CART_ATTRIBUTE = "CART";

    @Autowired
    private CartService cartService;

    @PostMapping(value = ID)
    public String putIntoCart(HttpServletRequest request, ModelMap modelMap
            , @PathVariable("id") int productId) {

        CartViewDto cartViewDto = (CartViewDto) request.getSession(true)
                .getAttribute(CART_ATTRIBUTE);
        if(cartViewDto == null){
            cartViewDto = new CartViewDto();
        }
        

        return SUCCESS_PAGE;
    }

    @GetMapping
    public String cartViewPage(ModelMap modelMap) {

        return CART_PAGE;
    }
}
