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
public class CategoryDto implements Serializable {

    private static final long serialVersionUID = 4187360651127209514L;

    private int id;
    private String name;
    private CategoryDto parentCategoryDto;
}