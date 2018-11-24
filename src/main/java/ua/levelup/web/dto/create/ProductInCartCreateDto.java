package ua.levelup.web.dto.create;

import lombok.*;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 *
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ProductInCartCreateDto implements Serializable {

    private static final long serialVersionUID = 7092923172785433351L;

    @Min(value = 1, message = "unacceptable_product_id")
    private int productId;

    @Min(value = 1, message = "unacceptable_product_quantity")
    private int count;

    public ProductInCartCreateDto(int productId, int count) {
        this.productId = productId;
        this.count = count;
    }
}