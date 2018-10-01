package ua.levelup.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class CartDto implements Serializable {

    private static final long serialVersionUID = 6585089281336642307L;

    private Map<Integer, Integer> productCountMap = new HashMap<>();    //ProductId - key, product count - value
    private Map<Integer,ProductDto> productDtoMap = new HashMap<>();          //ProductId - key, product - value
    private int size = 0;

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