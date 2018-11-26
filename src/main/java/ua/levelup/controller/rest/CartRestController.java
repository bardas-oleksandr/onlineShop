package ua.levelup.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.exception.RestException;
import ua.levelup.service.CartService;
import ua.levelup.web.dto.create.ProductInCartCreateDto;
import ua.levelup.web.dto.view.CartViewDto;
import ua.levelup.web.dto.view.ProductInCartViewDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 */
@RestController
@RequestMapping(value = "/rest/cart"
        , consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
        , produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CartRestController {

    private static final String CART_ATTRIBUTE = "cart";

    @Autowired
    private CartService cartService;

    @Autowired
    private Properties messagesProperties;

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    CartViewDto get(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        CartViewDto cart = (CartViewDto) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null || cart.getProductInCartViewDtoList().size() == 0) {
            //http status 404
            throw new RestException(HttpStatus.NOT_FOUND, messagesProperties.getProperty("EMPTY_CART"));
        }
        return cart;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    CartViewDto update(@Valid @RequestBody ProductInCartCreateDto productInCartCreateDto,
                       BindingResult result, HttpServletRequest request) {
        controllerUtils.checkValidationViolations(result);
        HttpSession session = request.getSession(true);
        CartViewDto cart = (CartViewDto) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new CartViewDto();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        cartService.putIntoCart(cart, productInCartCreateDto);
        return cart;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        CartViewDto cart = (CartViewDto) session.getAttribute(CART_ATTRIBUTE);
        if(cart != null){
            cart.setProductInCartViewDtoList(new ArrayList<ProductInCartViewDto>());
        }
    }
}
