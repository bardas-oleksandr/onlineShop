package ua.levelup.web.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class ProductDto implements Serializable {

    private static final long serialVersionUID = -6826469302682953337L;

    private int id;
    private String name;
    private float price;
    private boolean available;
    private String description;
    private CategoryDto categoryDto;
    private ManufacturerDto manufacturerDto;

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

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public ManufacturerDto getManufacturerDto() {
        return manufacturerDto;
    }

    public void setManufacturerDto(ManufacturerDto manufacturerDto) {
        this.manufacturerDto = manufacturerDto;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(price)
                .append(available)
                .append(description)
                .append(categoryDto)
                .append(manufacturerDto)
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
        ProductDto product = (ProductDto) other;
        return new EqualsBuilder()
                .append(id, product.id)
                .append(name, product.name)
                .append(price, product.price)
                .append(available, product.available)
                .append(description, product.description)
                .append(categoryDto, product.categoryDto)
                .append(manufacturerDto, product.manufacturerDto)
                .isEquals();
    }
}
