package ua.levelup.web.dto.create;

import lombok.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ProductCreateDto implements Serializable {

    private static final long serialVersionUID = -6156339104374405552L;

    @Size(max = 50, message = "unacceptable_product_name_length")
    @NotEmpty(message = "empty_product_name")
    private String name;

    private float price;

    private boolean available;

    @Size(max = 100, message = "unacceptable_product_description_length")
    private String description;

    @Min(value = 1, message = "unacceptable_category_id")
    private int categoryId;

    @Min(value = 1, message = "unacceptable_manufacturer_id")
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

    @AssertTrue(message = "unacceptable_price")
    public boolean isValidPrice(){
        return price >= 0.0f;
    }
}