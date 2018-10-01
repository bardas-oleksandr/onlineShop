package ua.levelup.web.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class OrderDto implements Serializable {

    private static final long serialVersionUID = 7990045339187007992L;

    private int id;
    private UserDto userDto;
    private int orderState;
    private Timestamp date;
    private String address;
    private int conditions;
    private List<OrderPositionDto> orderPositionList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int stateIndex) {
        this.orderState = stateIndex;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getConditions() {
        return conditions;
    }

    public void setConditions(int conditionsIndex) {
        this.conditions = conditionsIndex;
    }

    public List<OrderPositionDto> getOrderPositionList() {
        return orderPositionList;
    }

    public void setOrderPositionList(List<OrderPositionDto> orderPositionList) {
        this.orderPositionList = orderPositionList;
    }

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
