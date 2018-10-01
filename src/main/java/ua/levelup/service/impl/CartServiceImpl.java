package ua.levelup.service.impl;

import ua.levelup.converter.ProductConverter;
import ua.levelup.dao.ProductDao;
import ua.levelup.dao.support.DaoHolder;
import ua.levelup.model.Cart;
import ua.levelup.service.CartService;
import ua.levelup.validator.CartValidator;
import ua.levelup.web.dto.ProductDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CartServiceImpl implements CartService {

    private static final Logger logger = LogManager.getLogger();

    private ProductDao productDao = (ProductDao) DaoHolder.getDaoObject(DaoHolder.PRODUCT_DAO);

    @Override
    public void putIntoCart(Cart cart, Integer productId, Integer count) {
        CartValidator.validateCartEntry(productId, count);
        cart.putProduct(productId, count);
    }

    @Override
    public Map<Integer, ProductDto> retrieveCartProducts(Cart cart) {
        Map<Integer, ProductDto> map = new HashMap<>();
        Set<Integer> productIdSet = cart.getProductCountMap().keySet();
        for (Integer productId : productIdSet) {
            ua.levelup.model.Product product = productDao.getById(productId);
            map.put(productId, ProductConverter.asProductViewDto(product));
        }
        return map;
    }
}
