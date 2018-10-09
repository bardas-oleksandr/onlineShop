package ua.levelup.web.dto.create;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ProductCreateDto implements Serializable {

    private static final long serialVersionUID = -6156339104374405552L;

    private String name;
    private float price;
    private boolean available;
    private String description;
    private int categoryId;
    private int manufacturerId;

    public ProductCreateDto(String name, float price, boolean available, String description,
                            int categoryId, int manufacturerId) {
        this.name = name;
        this.price = price;
        this.available = available;
        this.description = description;
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
    }
}