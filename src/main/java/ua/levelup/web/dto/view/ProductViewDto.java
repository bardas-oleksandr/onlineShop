package ua.levelup.web.dto.view;

import lombok.*;

import java.io.Serializable;

/**
 *
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ProductViewDto implements Serializable {

    private static final long serialVersionUID = -6826469302682953337L;

    private int id;
    private String name;
    private float price;
    private boolean available;
    private String description;
    private CategoryViewDto categoryViewDto;
    private ManufacturerViewDto manufacturerViewDto;
}