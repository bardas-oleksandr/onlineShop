package ua.levelup.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@Setter
@Getter
public class ManufacturerDto implements Serializable {

    private static final long serialVersionUID = -1010367575480383830L;

    private int id;
    private String name;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
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
        ManufacturerDto manufacturer = (ManufacturerDto) other;
        return new EqualsBuilder()
                .append(id, manufacturer.id)
                .append(name, manufacturer.name)
                .isEquals();
    }
}