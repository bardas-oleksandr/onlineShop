package ua.levelup.web.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class CategoryDto implements Serializable {

    private static final long serialVersionUID = 4187360651127209514L;

    private int id;
    private String name;
    private CategoryDto parentCategoryDto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryDto getParentCategoryDto() {
        return parentCategoryDto;
    }

    public void setParentCategoryDto(CategoryDto parentCategoryDto) {
        this.parentCategoryDto = parentCategoryDto;
    }

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
