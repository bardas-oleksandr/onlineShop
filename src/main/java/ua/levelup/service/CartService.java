package ua.levelup.service;

import ua.levelup.web.dto.create.ProductInCartCreateDto;
import ua.levelup.web.dto.view.CartViewDto;

import javax.validation.Valid;

public interface CartService {
    void putIntoCart(CartViewDto cart, @Valid ProductInCartCreateDto productInCartCreateDto);
}