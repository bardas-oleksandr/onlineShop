package ua.levelup.converter.fromCreateDto;

import com.sun.istack.internal.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import ua.levelup.model.Product;
import ua.levelup.model.ProductInCart;
import ua.levelup.web.dto.create.ProductInCartCreateDto;

public class ProductInCartCreateDtoConverter
        implements Converter<ProductInCartCreateDto, ProductInCart> {

    @NotNull
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
