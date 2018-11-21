package ua.levelup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ua.levelup.dao.ProductDao;
import ua.levelup.model.Product;
import ua.levelup.service.CartService;
import ua.levelup.web.dto.create.ProductInCartCreateDto;
import ua.levelup.web.dto.view.CartViewDto;
import ua.levelup.web.dto.view.ProductInCartViewDto;
import ua.levelup.web.dto.view.ProductViewDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.Valid;

@Service("cartService")
public class CartServiceImpl implements CartService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ConversionService conversionService;

    @Override
    public void putIntoCart(CartViewDto cart, @Valid ProductInCartCreateDto productInCartCreateDto) {
        Product product = productDao.getById(productInCartCreateDto.getProductId());
        ProductViewDto productViewDto = conversionService.convert(product, ProductViewDto.class);
        int count = productInCartCreateDto.getCount();
        ProductInCartViewDto productInCartViewDto = new ProductInCartViewDto(productViewDto, count);
        cart.putIntoCart(productInCartViewDto);
    }
}