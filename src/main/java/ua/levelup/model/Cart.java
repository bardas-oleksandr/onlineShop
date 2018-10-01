package ua.levelup.model;

import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Cart implements Serializable {

    private static final long serialVersionUID = -1701271203258179128L;

    private Map<Integer, Integer> productCountMap = new HashMap<>();    //ProductId - key, product count - value
    private Map<Integer,Product> productMap = new HashMap<>();          //ProductId - key, product - value
    private int size = 0;

    public void putProduct(int productId, int productCount) {
        productCountMap.compute(productId,
                (key, value) -> value == null? productCount: value + productCount);
        size = productCountMap.size();
    }
}