package ua.levelup.web.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class OrderPositionViewDto implements Serializable {

    private static final long serialVersionUID = -2094362351988443765L;

    private int productId;
    private String productName;
    private int quantity;
    private float unitPrice;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
        OrderPositionViewDto orderPosition = (OrderPositionViewDto) other;
        return new EqualsBuilder()
                .append(productId, orderPosition.productId)
                .append(productName, orderPosition.productName)
                .append(quantity, orderPosition.quantity)
                .append(unitPrice, orderPosition.unitPrice)
                .isEquals();
    }
}
