package ua.levelup.web.dto.create;

import lombok.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SearchParamsCreateDto {

    @Min(value = 0, message = "negative_search_category_id")
    private int categoryId;

    @Min(value = 0, message = "negative_search_category_id")
    private int subcategoryId;

    @Min(value = 0, message = "negative_search_manufacturer_id")
    private int manufacturerId;

    private boolean availableOnly;

    private float minPrice;

    private float maxPrice;

    @Min(value = 0, message = "unacceptable_order_method")
    @Max(value = 2, message = "unacceptable_order_method")
    private int orderMethodIndex;

    public SearchParamsCreateDto(int categoryId, int subcategoryId, int manufacturerId,
                                 boolean availableOnly, float minPrice, float maxPrice,
                                 int orderMethodIndex) {
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.manufacturerId = manufacturerId;
        this.availableOnly = availableOnly;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.orderMethodIndex = orderMethodIndex;
    }

    @AssertTrue(message = "max_price_bigger_then_min_price")
    public boolean isValidPriceRange() {
        return minPrice <= maxPrice;
    }

    @AssertTrue(message = "unacceptable_price")
    public boolean isValidMinPrice() {
        return minPrice >= 0.0f;
    }
}