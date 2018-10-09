//package ua.levelup.service.impl;
//
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.stereotype.Service;
//import ua.levelup.dao.ProductDao;
//import ua.levelup.model.Cart;
//import ua.levelup.model.Product;
//import ua.levelup.service.CartService;
//import ua.levelup.validator.CartValidator;
//import ua.levelup.web.dto.view.CartViewDto;
//import ua.levelup.web.dto.view.ProductViewDto;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Setter
//@Service("cartService")
//public class CartServiceImpl implements CartService {
//
//    private static final Logger logger = LogManager.getLogger();
//
//    @Autowired
//    private ProductDao productDao;
//
//    @Autowired
//    ConversionService conversionService;
//
//    @Override
//    public void putIntoCart(Cart cart, Integer productId, Integer count) {
//        CartValidator.validateCartEntry(productId, count);
//
//        Product product = new Product();
//        product.setId(productId);
//        cart.putProduct(product, count);
//    }
//
//    @Override
//    public Map<Integer, ProductViewDto> retrieveCartProducts(CartViewDto cartDto) {
//        Map<Integer, ProductViewDto> map = new HashMap<>();
//
////        Set<Integer> productIdSet = cartDto.getProductCountMap().keySet();
////        for (Integer productId : productIdSet) {
////            Product product = productDao.getById(productId);
////            map.put(productId, conversionService.convert(product,ProductViewDto.class));
////        }
//
//        return map;
//    }
//}