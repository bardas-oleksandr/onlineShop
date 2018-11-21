package ua.levelup.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SearchParams implements Serializable {

    private static final long serialVersionUID = 8954670188103326530L;

    private int categoryId;
    private int manufacturerId;
    private boolean availableOnly;
    private float minPrice;
    private float maxPrice;
    private OrderMethod orderMethod;

    public SearchParams(int categoryId, int manufacturerId, boolean availableOnly,
                        float minPrice, float maxPrice, OrderMethod orderMethod) {
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
        this.availableOnly = availableOnly;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.orderMethod = orderMethod;
    }

    public enum OrderMethod {
        CHEAP_FIRST, CHEAP_LAST, PRODUCT_NAME;

        public static OrderMethod get(int orderMethod) {
            switch (orderMethod) {
                case 0:
                    return CHEAP_FIRST;
                case 1:
                    return CHEAP_LAST;
                case 2:
                    return PRODUCT_NAME;
                default:
                    throw new IllegalArgumentException("Illegal order method: " + orderMethod);
            }
        }
    }
}