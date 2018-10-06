package ua.levelup.web.dto.view;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class OrderViewDto implements Serializable {

    private static final long serialVersionUID = 7990045339187007992L;

    private int id;
    private UserViewDto userDto;
    private String address;
    private Timestamp date;
    private boolean payed;
    private int orderState;
    private int conditions;
    private List<OrderPositionViewDto> orderPositionList;
}