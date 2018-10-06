package ua.levelup.converter.toViewDto;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.ProductInCart;
import ua.levelup.web.dto.view.ProductInCartViewDto;

@Component("productInCartConverter")
@Getter
@Setter
public class ProductInCartConverter implements Converter<ProductInCart, ProductInCartViewDto> {

    @Autowired
    private ProductConverter productConverter;

    @NotNull
    @Override
    public ProductInCartViewDto convert(@NonNull ProductInCart productInCart) {
        ProductInCartViewDto productInCartViewDto = new ProductInCartViewDto();
        productInCartViewDto.setProductViewDto(productConverter.convert(productInCart.getProduct()));
        productInCartViewDto.setCount(productInCart.getCount());
        return productInCartViewDto;
    }
}