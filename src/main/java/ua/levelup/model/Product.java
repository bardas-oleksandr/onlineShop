package ua.levelup.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@Getter
@Setter
public class Product implements Serializable {

    private static final long serialVersionUID = -8198411312559108151L;

    private int id;
    private String name;
    private float price;
    private boolean available;
    private String description;
    private Category category;
    private Manufacturer manufacturer;

    public Product() { }

    public Product(String name, float price, boolean available, String description,
                   Category category, Manufacturer manufacturer) {
        this.name = name;
        this.price = price;
        this.available = available;
        this.description = description;
        this.category = category;
        this.manufacturer = manufacturer;
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
                .append(category, product.category)
                .append(manufacturer, product.manufacturer)
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
                .append(category)
                .append(manufacturer)
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
                ", category=" + category +
                ", manufacturer=" + manufacturer +
                '}';
    }
}