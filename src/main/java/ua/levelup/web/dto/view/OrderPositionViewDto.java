package ua.levelup.web.dto.view;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class OrderPositionViewDto implements Serializable {

    private static final long serialVersionUID = -2094362351988443765L;

    private int orderId;
    private int productId;
    private String productName;
    private int quantity;
    private float unitPrice;
}