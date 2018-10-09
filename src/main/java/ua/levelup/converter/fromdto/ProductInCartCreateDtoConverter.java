package ua.levelup.converter.fromdto;

import org.springframework.core.convert.converter.Converter;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.levelup.model.Product;
import ua.levelup.model.ProductInCart;
import ua.levelup.web.dto.create.ProductInCartCreateDto;

/**
 *
 */
@Component("productInCartCreateDtoConverter")
public class ProductInCartCreateDtoConverter
        implements Converter<ProductInCartCreateDto, ProductInCart> {

    @Override
    public ProductInCart convert(@NonNull ProductInCartCreateDto productInCartCreateDto) {
        ProductInCart productInCart = new ProductInCart();
        Product product = new Product();
        product.setId(productInCartCreateDto.getProductId());
        productInCart.setProduct(product);
        productInCart.setCount(productInCartCreateDto.getCount());
        return productInCart;
    }
}