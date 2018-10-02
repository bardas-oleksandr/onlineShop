package ua.levelup.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.levelup.converter.ProductConverter;
import ua.levelup.dao.ProductDao;
import ua.levelup.model.Cart;
import ua.levelup.model.Product;
import ua.levelup.service.CartService;
import ua.levelup.validator.CartValidator;
import ua.levelup.web.dto.CartDto;
import ua.levelup.web.dto.ProductDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service("cartService")
@Getter
@Setter
public class CartServiceImpl implements CartService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ProductDao productDao;

    @Override
    public void putIntoCart(Cart cart, Integer productId, Integer count) {
        CartValidator.validateCartEntry(productId, count);
        cart.putProduct(productId, count);
    }

    @Override
    public Map<Integer, ProductDto> retrieveCartProducts(CartDto cartDto) {
        Map<Integer, ProductDto> map = new HashMap<>();
        Set<Integer> productIdSet = cartDto.getProductCountMap().keySet();
        for (Integer productId : productIdSet) {
            Product product = productDao.getById(productId);
            map.put(productId, ProductConverter.asProductDto(product));
        }
        return map;
    }
}
