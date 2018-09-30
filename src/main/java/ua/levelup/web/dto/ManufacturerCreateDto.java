package ua.levelup.web.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class ManufacturerCreateDto implements Serializable {

    private static final long serialVersionUID = 3143719210081939921L;

    private String name;

    public ManufacturerCreateDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
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
        ManufacturerCreateDto manufacturer = (ManufacturerCreateDto) other;
        return new EqualsBuilder()
                .append(name, manufacturer.name)
                .isEquals();
    }
}
