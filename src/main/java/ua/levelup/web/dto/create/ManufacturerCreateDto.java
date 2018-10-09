package ua.levelup.web.dto.create;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ManufacturerCreateDto implements Serializable {

    private static final long serialVersionUID = -152364096765870167L;

    private String name;

    public ManufacturerCreateDto(String name) {
        this.name = name;
    }
}