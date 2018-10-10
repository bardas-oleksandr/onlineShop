package ua.levelup.web.dto.create;

import lombok.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
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

    private float unitPrice;

    public OrderPositionCreateDto(int orderId, int productId, int quantity, float unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    @AssertTrue(message = "unacceptable_price")
    public boolean isValidPrice(){
        return unitPrice >= 0.0f;
    }
}