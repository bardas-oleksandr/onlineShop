package ua.levelup.web.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class OrderPositionCreateDto implements Serializable {

    private static final long serialVersionUID = -139510653392433018L;

    private int productId;
    private int quantity;
    private float unitPrice;

    public OrderPositionCreateDto(int productId, int quantity, float unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(productId)
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
        OrderPositionCreateDto orderPosition = (OrderPositionCreateDto) other;
        return new EqualsBuilder()
                .append(productId, orderPosition.productId)
                .append(quantity, orderPosition.quantity)
                .append(unitPrice, orderPosition.unitPrice)
                .isEquals();
    }
}
