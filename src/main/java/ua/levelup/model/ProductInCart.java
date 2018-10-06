package ua.levelup.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductInCart implements Serializable {

    private static final long serialVersionUID = -332168374619256111L;

    private Product product;
    private int count;

    public ProductInCart(@NonNull Product product, int count){
        this.product = product;
        this.count = count;
    }
}
