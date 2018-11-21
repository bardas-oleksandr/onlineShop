package ua.levelup.web.dto;

import lombok.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SearchParamsDto {

    @Min(value = 0, message = "negative_search_category_id")
    private int categoryId;

    @Min(value = 0, message = "negative_search_category_id")
    private int subcategoryId;

    @Min(value = 0, message = "negative_search_manufacturer_id")
    private int manufacturerId;

    private boolean availableOnly;

    private Float minPrice;

    private Float maxPrice;

    @Min(value = 0, message = "unacceptable_order_method")
    @Max(value = 2, message = "unacceptable_order_method")
    private int orderMethodIndex;

    public SearchParamsDto(int categoryId, int subcategoryId, int manufacturerId,
                           boolean availableOnly, Float minPrice, Float maxPrice,
                           int orderMethodIndex) {
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.manufacturerId = manufacturerId;
        this.availableOnly = availableOnly;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.orderMethodIndex = orderMethodIndex;
    }

    @AssertTrue(message = "min_price_bigger_then_max_price")
    public boolean isValidPriceRange() {
        return minPrice != null && maxPrice != null && minPrice <= maxPrice;
    }

    @AssertTrue(message = "unacceptable_price")
    public boolean isValidMinPrice() {
        return minPrice != null && minPrice >= 0.0f;
    }
}