package ua.levelup.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@Setter
@Getter
public class OrderPositionDto implements Serializable {

    private static final long serialVersionUID = -2094362351988443765L;

    private int orderId;
    private int productId;
    private String productName;
    private int quantity;
    private float unitPrice;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(orderId)
                .append(productId)
                .append(productName)
                .append(quantity)
                .append(unitPrice)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        OrderPositionDto orderPosition = (OrderPositionDto) other;
        return new EqualsBuilder()
                .append(orderId, orderPosition.orderId)
                .append(productId, orderPosition.productId)
                .append(productName, orderPosition.productName)
                .append(quantity, orderPosition.quantity)
                .append(unitPrice, orderPosition.unitPrice)
                .isEquals();
    }
}