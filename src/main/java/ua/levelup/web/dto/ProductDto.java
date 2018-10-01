package ua.levelup.web.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ProductDto implements Serializable {

    private static final long serialVersionUID = -6826469302682953337L;

    private int id;
    private String name;
    private float price;
    private boolean available;
    private String description;
    private CategoryDto categoryDto;
    private ManufacturerDto manufacturerDto;
}