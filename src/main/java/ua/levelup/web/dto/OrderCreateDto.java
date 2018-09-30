package ua.levelup.web.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderCreateDto implements Serializable {

    private static final long serialVersionUID = 6259849726299345043L;

    private int userId;
    private int state;
    private Timestamp date;
    private String address;
    private int conditions;
    private List<OrderPositionCreateDto> orderPositionList;

    public OrderCreateDto(int userId, int state, Timestamp date, String address, int conditions,
                          List<OrderPositionCreateDto> orderPositionList) {
        this.userId = userId;
        this.state = state;
        this.date = date;
        this.address = address;
        this.conditions = conditions;
        this.orderPositionList = orderPositionList;
    }

    public OrderCreateDto(int userId, int state, Timestamp date, String address, int conditions) {
        this.userId = userId;
        this.state = state;
        this.date = date;
        this.address = address;
        this.conditions = conditions;
        this.orderPositionList = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getState() {
        return state;
    }

    public void setState(int stateIndex) {
        this.state = stateIndex;
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

    public List<OrderPositionCreateDto> getOrderPositionList() {
        return orderPositionList;
    }

    public void setOrderPositionList(List<OrderPositionCreateDto> orderPositionList) {
        this.orderPositionList = orderPositionList;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userId)
                .append(state)
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
        OrderCreateDto order = (OrderCreateDto) other;
        return new EqualsBuilder()
                .append(userId, order.userId)
                .append(state, order.state)
                .append(date, order.date)
                .append(address, order.address)
                .append(conditions, order.conditions)
                .append(orderPositionList, order.orderPositionList)
                .isEquals();
    }
}
