package ua.levelup.web.dto.create;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ManufacturerCreateDto implements Serializable {

    private static final long serialVersionUID = -152364096765870167L;

    @Size(max = 50, message = "unacceptable_manufacturer_name_length")
    @NotEmpty(message = "empty_manufacturer_name")
    private String name;

    public ManufacturerCreateDto(String name) {
        this.name = name;
    }
}