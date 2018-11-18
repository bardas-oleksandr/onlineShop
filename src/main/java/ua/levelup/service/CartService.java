package ua.levelup.service;

import ua.levelup.web.dto.create.ProductInCartCreateDto;
import ua.levelup.web.dto.view.CartViewDto;
import ua.levelup.web.dto.view.ProductViewDto;

import javax.validation.Valid;
import java.util.Map;

public interface CartService {
    void putIntoCart(CartViewDto cart, @Valid ProductInCartCreateDto productInCartCreateDto);
    Map<Integer, ProductViewDto> retrieveCartProducts(CartViewDto cartDto);
}
