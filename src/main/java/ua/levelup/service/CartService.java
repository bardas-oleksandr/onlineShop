package ua.levelup.service;

import ua.levelup.model.Cart;
import ua.levelup.web.dto.view.CartViewDto;
import ua.levelup.web.dto.view.ProductViewDto;

import java.util.Map;

public interface CartService {
    void putIntoCart(Cart cart, Integer productId, Integer count);
    Map<Integer, ProductViewDto> retrieveCartProducts(CartViewDto cartDto);
}
