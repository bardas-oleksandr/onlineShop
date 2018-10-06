package ua.levelup.converter.toViewDto;

import com.sun.istack.internal.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import ua.levelup.model.Cart;
import ua.levelup.model.ProductInCart;
import ua.levelup.web.dto.view.CartViewDto;
import ua.levelup.web.dto.view.ProductInCartViewDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartConverter implements Converter<Cart, CartViewDto> {

    @Autowired
    private ProductInCartConverter productInCartConverter;

    @NotNull
    @Override
    public CartViewDto convert(@NonNull Cart cart) {
        CartViewDto cartViewDto = new CartViewDto();
        List<ProductInCartViewDto> productInCartViewDtoList = convertList(cart
                .getProductInCartList());
        cartViewDto.setProductInCartViewDtoList(productInCartViewDtoList);
        return cartViewDto;
    }

    private List<ProductInCartViewDto> convertList(List<ProductInCart> list){
        List<ProductInCartViewDto> result = new ArrayList<>();
        list.forEach((productInCart) -> result
                .add(productInCartConverter.convert(productInCart)));
        return result;
    }
}
