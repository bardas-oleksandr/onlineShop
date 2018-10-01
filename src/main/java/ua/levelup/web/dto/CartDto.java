package ua.levelup.web.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CartDto implements Serializable {

    private static final long serialVersionUID = 6585089281336642307L;

    private Map<Integer, Integer> productCountMap = new HashMap<>();    //ProductId - key, product count - value
    private Map<Integer,ProductDto> productDtoMap = new HashMap<>();          //ProductId - key, product - value
    private int size = 0;
}