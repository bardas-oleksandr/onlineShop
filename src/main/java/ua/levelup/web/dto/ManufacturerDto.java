package ua.levelup.web.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ManufacturerDto implements Serializable {

    private static final long serialVersionUID = -1010367575480383830L;

    private int id;
    private String name;
}