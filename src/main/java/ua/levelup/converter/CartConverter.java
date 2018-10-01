package ua.levelup.converter;

import ua.levelup.model.Cart;
import ua.levelup.model.Product;
import ua.levelup.web.dto.CartDto;
import ua.levelup.web.dto.ProductDto;

import java.util.LinkedHashMap;
import java.util.Map;

public enum CartConverter {
    ;

    public static CartDto asCartDto(Cart cart) {
        CartDto cartDto = new CartDto();
        Map<Integer, Integer> productCountMap= cart.getProductCountMap();
        Map<Integer, Integer> productDtoCountMap = new LinkedHashMap<>();
        productCountMap.forEach((key,count)->productDtoCountMap.put(key,count));
        cartDto.setProductCountMap(productDtoCountMap);

        Map<Integer, Product> productMap = cart.getProductMap();
        Map<Integer, ProductDto> productDtoMap = new LinkedHashMap<>();
        productMap.forEach((key, product) -> productDtoMap
                .put(key, ProductConverter.asProductViewDto(product)));
        cartDto.setProductDtoMap(productDtoMap);

        cartDto.setSize(cart.getSize());
        return cartDto;
    }
}
