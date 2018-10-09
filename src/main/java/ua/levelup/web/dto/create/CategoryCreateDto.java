package ua.levelup.web.dto.create;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CategoryCreateDto implements Serializable {

    private static final long serialVersionUID = -8018737587342657430L;

    @Size(max = 50, message = "unacceptable_category_name_length")
    @NotEmpty(message = "empty_category_name")
    private String name;

    @Min(value = 0, message = "negative_parent_category_id")
    private int parentCategoryId;

    public CategoryCreateDto(String name, int parentCategoryId) {
        this.name = name;
        this.parentCategoryId = parentCategoryId;
    }
}