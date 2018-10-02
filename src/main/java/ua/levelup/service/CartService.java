package ua.levelup.service;

import ua.levelup.model.Cart;
import ua.levelup.web.dto.CartDto;
import ua.levelup.web.dto.ProductDto;

import java.util.Map;

public interface CartService {
    void putIntoCart(Cart cart, Integer productId, Integer count);
    Map<Integer, ProductDto> retrieveCartProducts(CartDto cartDto);
}
