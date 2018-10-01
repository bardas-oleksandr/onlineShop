package ua.levelup.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Manufacturer implements Serializable {

    private static final long serialVersionUID = -3697471520398417681L;

    private int id;
    private String name;

    public Manufacturer(String name) {
        this.name = name;
    }
}