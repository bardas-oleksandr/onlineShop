package ua.levelup.web.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class ProductCreateDto implements Serializable {

    private static final long serialVersionUID = -5432365636923923098L;

    private String name;
    private float price;
    private boolean available;
    private String description;
    private int categoryId;
    private int manufacturerId;

    public ProductCreateDto(){}

    public ProductCreateDto(String name, float price, boolean available, String description,
                            int categoryId, int manufacturerId) {
        this.name = name;
        this.price = price;
        this.available = available;
        this.description = description;
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(price)
                .append(available)
                .append(description)
                .append(categoryId)
                .append(manufacturerId)
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
        ProductCreateDto product = (ProductCreateDto) other;
        return new EqualsBuilder()
                .append(name, product.name)
                .append(price, product.price)
                .append(available, product.available)
                .append(description, product.description)
                .append(categoryId, product.categoryId)
                .append(manufacturerId, product.manufacturerId)
                .isEquals();
    }
}
