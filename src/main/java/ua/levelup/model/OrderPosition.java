package ua.levelup.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderPosition implements Serializable {

    private static final long serialVersionUID = -3811193438177918259L;

    private int orderId;
    private int productId;
    private String productName;
    private int quantity;
    private float unitPrice;

    public OrderPosition(int orderId, int productId, int quantity, float unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}