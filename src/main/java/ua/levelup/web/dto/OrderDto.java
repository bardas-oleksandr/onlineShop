package ua.levelup.web.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class OrderDto implements Serializable {

    private static final long serialVersionUID = 7990045339187007992L;

    private int id;
    private UserDto userDto;
    private int orderState;
    private Timestamp date;
    private String address;
    private int conditions;
    private List<OrderPositionDto> orderPositionList;
}