package ua.levelup.web.dto.create;

import lombok.*;
import ua.levelup.model.Category;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CategoryCreateDto implements Serializable {

    private static final long serialVersionUID = -8018737587342657430L;

    private String name;
    private int parentCategoryId;
}
