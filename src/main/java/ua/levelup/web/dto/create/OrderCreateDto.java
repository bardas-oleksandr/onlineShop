package ua.levelup.web.dto.create;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class OrderCreateDto implements Serializable {

    private static final long serialVersionUID = -7382801572605686420L;

    private int userId;
    private String address;
    private Timestamp date;
    private int paymentConditionsIndex;
}
