package ua.levelup.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class OrderPosition implements Serializable {

    private static final long serialVersionUID = -3811193438177918259L;

    private int orderId;
    private int productId;
    private String productName;
    private int quantity;
    private float unitPrice;

    public OrderPosition() { }

    public OrderPosition(int orderId, int productId, String productName, int quantity, float unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

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
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        OrderPosition orderPosition = (OrderPosition) other;
        return new EqualsBuilder()
                .append(orderId, orderPosition.orderId)
                .append(productId, orderPosition.productId)
                .append(productName, orderPosition.productName)
                .append(quantity, orderPosition.quantity)
                .append(unitPrice, orderPosition.unitPrice)
                .isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17, 37)
                .append(orderId)
                .append(productId)
                .append(productName)
                .append(quantity)
                .append(unitPrice)
                .toHashCode();
    }

    @Override
    public String toString(){
        return "OrderPosition{" +
                " orderId=" + orderId +
                " productId=" + productId +
                ", productName=" + productName +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
