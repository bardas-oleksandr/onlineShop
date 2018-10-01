package ua.levelup.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@Setter
@Getter
public class CategoryDto implements Serializable {

    private static final long serialVersionUID = 4187360651127209514L;

    private int id;
    private String name;
    private CategoryDto parentCategoryDto;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(parentCategoryDto)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        CategoryDto category = (CategoryDto) other;
        return new EqualsBuilder()
                .append(id, category.id)
                .append(name, category.name)
                .append(parentCategoryDto, category.parentCategoryDto)
                .isEquals();
    }
}