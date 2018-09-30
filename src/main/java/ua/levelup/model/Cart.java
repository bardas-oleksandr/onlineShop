package ua.levelup.model;

import ua.levelup.web.dto.ProductViewDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {

    private static final long serialVersionUID = -1701271203258179128L;

    private Map<Integer, Integer> productCountMap = new HashMap<>();    //ProductId - key, product count - value
    private Map<Integer, ProductViewDto> productViewDtoMap = new HashMap<>(); //ProductId - key
    private int size = 0;

    public void putProduct(int productId, int productCount) {
        productCountMap.compute(productId,
                (key, value) -> value == null? productCount: value + productCount);
        size = productCountMap.size();
    }

    public Map<Integer, Integer> getProductCountMap() {
        return productCountMap;
    }

    public void setProductCountMap(Map<Integer, Integer> productCountMap) {
        this.productCountMap = productCountMap;
    }

    public Map<Integer, ProductViewDto> getProductViewDtoMap() {
        return productViewDtoMap;
    }

    public void setProductViewDtoMap(Map<Integer, ProductViewDto> productViewDtoMap) {
        this.productViewDtoMap = productViewDtoMap;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize(){
        return size;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Cart cart = (Cart) other;
        return new EqualsBuilder()
                .append(productCountMap, cart.productCountMap)
                .append(productViewDtoMap, cart.productViewDtoMap)
                .append(size, cart.size)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(productCountMap)
                .append(productViewDtoMap)
                .append(size)
                .toHashCode();
    }
}
