package ua.levelup.web.dto.view;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CartViewDto implements Serializable {

    private static final long serialVersionUID = 6585089281336642307L;

    private List<ProductInCartViewDto> productInCartViewDtoList;

    public CartViewDto(){
        this.productInCartViewDtoList = new ArrayList<>();
    }

    public void putIntoCart(@NonNull ProductInCartViewDto productInCartViewDto) {
        ProductViewDto productViewDto = productInCartViewDto.getProductViewDto();
        int productCount = productInCartViewDto.getCount();
        Optional<ProductInCartViewDto> productInCartOptional = productInCartViewDtoList.stream()
                .filter((item) -> item.getProductViewDto().getId() == productViewDto.getId()).findFirst();
        if (productInCartOptional.isPresent()){
            ProductInCartViewDto productInCart = productInCartOptional.get();
            productInCart.setCount(productInCart.getCount() + productCount);
        }else{
            productInCartViewDtoList.add(productInCartViewDto);
        }
    }
}