package ua.levelup.model;

import ua.levelup.model.support.OrderState;
import ua.levelup.model.support.PaymentConditions;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    private static final long serialVersionUID = -8048642462864555907L;

    private int id;
    private int userId;
    private String userName;
    private OrderState state;
    private Timestamp date;
    private String address;
    private PaymentConditions conditions;
    private List<OrderPosition> orderPositionList;

    public Order() { }

    public Order(int userId, int state, Timestamp date, String address, int conditions, List<OrderPosition> orderPositionList) {
        this.userId = userId;
        this.state = OrderState.getOrderState(state);
        this.date = date;
        this.address = address;
        this.conditions = PaymentConditions.getPaymentConditions(conditions);
        this.orderPositionList = orderPositionList;
    }

    public Order(int userId, int state, Timestamp date, String address, int conditions) {
        this.userId = userId;
        this.state = OrderState.getOrderState(state);
        this.date = date;
        this.address = address;
        this.conditions = PaymentConditions.getPaymentConditions(conditions);
        this.orderPositionList = new ArrayList<>();
    }

    public void addOrderPosition(OrderPosition orderPosition){
        this.orderPositionList.add(orderPosition);
    }

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

    public OrderState getState() {
        return state;
    }

    public void setState(int state) {
        this.state = OrderState.getOrderState(state);
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

    public PaymentConditions getConditions() {
        return conditions;
    }

    public void setConditions(int conditions) {
        this.conditions = PaymentConditions.getPaymentConditions(conditions);
    }

    public List<OrderPosition> getOrderPositionList() {
        return orderPositionList;
    }

    public void setOrderPositionList(List<OrderPosition> orderPositionList) {
        this.orderPositionList = orderPositionList;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Order order = (Order) other;
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

    @Override
    public int hashCode(){
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
    public String toString(){
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName=" + userName +
                ", state='" + state + '\'' +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", conditions='" + conditions + '\'' +
                ", orderPositionList=" + orderPositionList +
                '}';
    }
}
