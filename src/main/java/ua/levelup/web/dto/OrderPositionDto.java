package ua.levelup.web.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class OrderPositionDto implements Serializable {

    private static final long serialVersionUID = -2094362351988443765L;

    private int orderId;
    private int productId;
    private String productName;
    private int quantity;
    private float unitPrice;
}