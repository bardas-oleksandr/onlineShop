package ua.levelup.web.dto.create;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ProductInCartCreateDto implements Serializable {

    private static final long serialVersionUID = 7092923172785433351L;

    private int productId;
    private int count;

    public ProductInCartCreateDto(int productId, int count) {
        this.productId = productId;
        this.count = count;
    }
}
