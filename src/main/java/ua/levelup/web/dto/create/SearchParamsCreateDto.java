package ua.levelup.web.dto.create;

import lombok.*;
import ua.levelup.dao.support.OrderMethod;
import ua.levelup.model.Product;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SearchParamsCreateDto {

    @NotNull(message = "empty_search_product_field")
    private Product product;

    @Min(value = 0, message = "negative_search_category_id")
    private int categoryId;

    @Min(value = 0, message = "negative_search_category_id")
    private int subcategoryId;

    private float minPrice;

    private float maxPrice;

    @NotNull(message = "empty_order_method")
    private OrderMethod orderMethod;

    public SearchParamsCreateDto(Product product, int categoryId, int subcategoryId,
                                 float minPrice, float maxPrice, OrderMethod orderMethod) {
        this.product = product;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.orderMethod = orderMethod;
    }

    @AssertTrue(message = "max_price_bigger_then_min_price")
    public boolean isValidPriceRange(){
        return minPrice <= maxPrice;
    }

    @AssertTrue(message = "unacceptable_price")
    public boolean isValidMinPrice(){
        return minPrice >= 0.0f;
    }
}