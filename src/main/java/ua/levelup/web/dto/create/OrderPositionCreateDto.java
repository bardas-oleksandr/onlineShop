package ua.levelup.web.dto.create;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class OrderPositionCreateDto implements Serializable {

    private static final long serialVersionUID = -8540540991157476779L;

    private int orderId;
    private int productId;
    private String productName;
    private int quantity;
    private float unitPrice;
}
