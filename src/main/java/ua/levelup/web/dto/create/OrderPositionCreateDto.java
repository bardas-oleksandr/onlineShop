package ua.levelup.web.dto.create;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class OrderPositionCreateDto implements Serializable {

    private static final long serialVersionUID = -8540540991157476779L;

    @Min(value = 1, message = "unacceptable_order_id")
    private int orderId;

    @Min(value = 1, message = "unacceptable_product_id")
    private int productId;

    @Min(value = 1, message = "unacceptable_product_quantity")
    private int quantity;

    @Min(value = 0, message = "unacceptable_price")
    private float unitPrice;

    public OrderPositionCreateDto(int orderId, int productId, int quantity, float unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}