package ua.levelup.service;

import ua.levelup.web.dto.create.ProductInCartCreateDto;
import ua.levelup.web.dto.view.CartViewDto;

public interface CartService {
    void putIntoCart(CartViewDto cart, ProductInCartCreateDto productInCartCreateDto);
}