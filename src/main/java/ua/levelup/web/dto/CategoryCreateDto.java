package ua.levelup.web.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class CategoryCreateDto implements Serializable {

    private static final long serialVersionUID = 4732258965789243311L;

    private String name;
    private int parentId;

    public CategoryCreateDto(String name, int parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(parentId)
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
        CategoryCreateDto category = (CategoryCreateDto) other;
        return new EqualsBuilder()
                .append(name, category.name)
                .append(parentId, category.parentId)
                .isEquals();
    }
}
