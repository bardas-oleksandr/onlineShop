package ua.levelup.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@Getter
@Setter
public class Manufacturer implements Serializable {

    private static final long serialVersionUID = -3697471520398417681L;

    private int id;
    private String name;

    public Manufacturer() { }

    public Manufacturer(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Manufacturer manufacturer = (Manufacturer) other;
        return new EqualsBuilder()
                .append(id, manufacturer.id)
                .append(name, manufacturer.name)
                .isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString(){
        return "Manufacturer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
