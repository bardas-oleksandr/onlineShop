package ua.levelup.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = -8198411312559108151L;

    private int id;
    private String name;
    private float price;
    private boolean available;
    private String description;
    private int categoryId;
    private String categoryName;
    private int manufacturerId;
    private String manufacturerName;

    public Product() { }

    public Product(String name, float price, boolean available, String description, int categoryId, int manufacturerId) {
        this.name = name;
        this.price = price;
        this.available = available;
        this.description = description;
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Product product = (Product) other;
        return new EqualsBuilder()
                .append(id, product.id)
                .append(name, product.name)
                .append(price, product.price)
                .append(available, product.available)
                .append(description, product.description)
                .append(categoryId, product.categoryId)
                .append(categoryName, product.categoryName)
                .append(manufacturerId, product.manufacturerId)
                .append(manufacturerName, product.manufacturerName)
                .isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(price)
                .append(available)
                .append(description)
                .append(categoryId)
                .append(categoryName)
                .append(manufacturerId)
                .append(manufacturerName)
                .toHashCode();
    }

    @Override
    public String toString(){
        return "Product{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                ", available=" + available +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", manufacturerId=" + manufacturerId +
                ", manufacturerName='" + manufacturerName + '\'' +
                '}';
    }
}
