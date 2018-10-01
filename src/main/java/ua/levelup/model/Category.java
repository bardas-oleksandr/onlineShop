package ua.levelup.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Category implements Serializable {

    private static final long serialVersionUID = -7331747245897027322L;

    private int id;
    private String name;
    private Category parentCategory;

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }
}