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
public class CategoryViewDto implements Serializable {

    private static final long serialVersionUID = 4187360651127209514L;

    private int id;
    private String name;
    private CategoryViewDto parentCategoryViewDto;
}