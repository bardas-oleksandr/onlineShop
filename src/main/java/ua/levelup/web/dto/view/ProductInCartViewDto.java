package ua.levelup.web.dto.view;

import lombok.*;

import java.io.Serializable;

/**
 *
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ProductInCartViewDto implements Serializable {

    private static final long serialVersionUID = 1708169491665365058L;

    private ProductViewDto productViewDto;
    private int count;

    public ProductInCartViewDto(ProductViewDto productViewDto, int count) {
        this.productViewDto = productViewDto;
        this.count = count;
    }
}