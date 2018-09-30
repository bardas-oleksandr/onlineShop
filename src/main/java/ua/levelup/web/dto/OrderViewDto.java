package ua.levelup.web.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class OrderViewDto implements Serializable {

    private static final long serialVersionUID = 7990045339187007992L;

    private int id;
    private int userId;
    private String userName;
    private int state;
    private Timestamp date;
    private String address;
    private int conditions;
    private List<OrderPositionViewDto> orderPositionList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<OrderPositionViewDto> getOrderPositionList() {
        return orderPositionList;
    }

    public void setOrderPositionList(List<OrderPositionViewDto> orderPositionList) {
        this.orderPositionList = orderPositionList;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(userId)
                .append(userName)
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
        OrderViewDto order = (OrderViewDto) other;
        return new EqualsBuilder()
                .append(id, order.id)
                .append(userId, order.userId)
                .append(userName, order.userName)
                .append(state, order.state)
                .append(date, order.date)
                .append(address, order.address)
                .append(conditions, order.conditions)
                .append(orderPositionList, order.orderPositionList)
                .isEquals();
    }
}
