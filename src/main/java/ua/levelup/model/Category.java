package ua.levelup.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@Getter
@Setter
public class Category implements Serializable {

    private static final long serialVersionUID = -7331747245897027322L;

    private int id;
    private String name;
    private Category parentCategory;

    public Category() {
    }

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Category category = (Category) other;
        return new EqualsBuilder()
                .append(id, category.id)
                .append(name, category.name)
                .append(parentCategory, category.parentCategory)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(parentCategory)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent category=" + parentCategory +
                '}';
    }
}
