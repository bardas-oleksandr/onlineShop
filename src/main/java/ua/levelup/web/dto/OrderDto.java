package ua.levelup.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
public class OrderDto implements Serializable {

    private static final long serialVersionUID = 7990045339187007992L;

    private int id;
    private UserDto userDto;
    private int orderState;
    private Timestamp date;
    private String address;
    private int conditions;
    private List<OrderPositionDto> orderPositionList;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(userDto)
                .append(orderState)
                .append(date)
                .append(address)
                .append(conditions)
                .append(orderPositionList)
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
        OrderDto order = (OrderDto) other;
        return new EqualsBuilder()
                .append(id, order.id)
                .append(userDto, order.userDto)
                .append(orderState, order.orderState)
                .append(date, order.date)
                .append(address, order.address)
                .append(conditions, order.conditions)
                .append(orderPositionList, order.orderPositionList)
                .isEquals();
    }
}