package ua.levelup.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Product implements Serializable {

    private static final long serialVersionUID = -8198411312559108151L;

    private int id;
    private String name;
    private float price;
    private boolean available;
    private String description;
    private Category category;
    private Manufacturer manufacturer;

    public Product(String name, float price, boolean available, String description,
                   Category category, Manufacturer manufacturer) {
        this.name = name;
        this.price = price;
        this.available = available;
        this.description = description;
        this.category = category;
        this.manufacturer = manufacturer;
    }
}