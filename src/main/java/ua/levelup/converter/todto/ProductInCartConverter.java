package ua.levelup.converter.todto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.ProductInCart;
import ua.levelup.web.dto.view.ProductInCartViewDto;

/**
 *
 */
@Component("productInCartConverter")
public class ProductInCartConverter implements Converter<ProductInCart, ProductInCartViewDto> {

    @Autowired
    ProductConverter productConverter;

    @Override
    public ProductInCartViewDto convert(@NonNull ProductInCart productInCart) {
        ProductInCartViewDto productInCartViewDto = new ProductInCartViewDto();
        productInCartViewDto.setProductViewDto(productConverter.convert(productInCart.getProduct()));
        productInCartViewDto.setCount(productInCart.getCount());
        return productInCartViewDto;
    }
}