package ua.levelup.web.dto.view;

import lombok.*;

import java.io.Serializable;

/**
 *
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ManufacturerViewDto implements Serializable {

    private static final long serialVersionUID = -1010367575480383830L;

    private int id;
    private String name;
}