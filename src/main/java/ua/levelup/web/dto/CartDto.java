package ua.levelup.web.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CartDto implements Serializable {

    private static final long serialVersionUID = 6585089281336642307L;

    private Map<Integer, Integer> productCountMap = new HashMap<>();    //ProductId - key, product count - value
    private Map<Integer,ProductDto> productDtoMap = new HashMap<>();          //ProductId - key, product - value
    private int size = 0;

    public Map<Integer, Integer> getProductCountMap() {
        return productCountMap;
    }

    public void setProductCountMap(Map<Integer, Integer> productCountMap) {
        this.productCountMap = productCountMap;
    }

    public Map<Integer, ProductDto> getProductDtoMap() {
        return productDtoMap;
    }

    public void setProductDtoMap(Map<Integer, ProductDto> productDtoMap) {
        this.productDtoMap = productDtoMap;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        CartDto cartDto = (CartDto) other;
        return new EqualsBuilder()
                .append(productCountMap, cartDto.productCountMap)
                .append(productDtoMap, cartDto.productDtoMap)
                .append(size, cartDto.size)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(productCountMap)
                .append(productDtoMap)
                .append(size)
                .toHashCode();
    }
}
