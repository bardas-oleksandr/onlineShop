package ua.levelup.web.dto.view;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
}