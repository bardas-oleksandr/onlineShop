package ua.levelup.model;

import lombok.Getter;
import lombok.Setter;
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

    @Getter @Setter private int id;
    @Getter @Setter private User user;
    @Getter private OrderState orderState;
    @Getter @Setter private Timestamp date;
    @Getter @Setter private String address;
    @Getter private PaymentConditions paymentConditions;
    @Getter @Setter private List<OrderPosition> orderPositionList;

    public Order() { }

    public Order(User user, int orderStateIndex, Timestamp date, String address, int paymentConditionsIndex) {
        this.user = user;
        this.orderState = OrderState.getOrderState(orderStateIndex);
        this.date = date;
        this.address = address;
        this.paymentConditions = PaymentConditions.getPaymentConditions(paymentConditionsIndex);
        this.orderPositionList = new ArrayList<>();
    }

    public void addOrderPosition(OrderPosition orderPosition){
        this.orderPositionList.add(orderPosition);
    }

    public void setOrderState(int orderState) {
        this.orderState = OrderState.getOrderState(orderState);
    }

    public void setPaymentConditions(int paymentConditionsIndex) {
        this.paymentConditions = PaymentConditions.getPaymentConditions(paymentConditionsIndex);
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
                .append(user, order.user)
                .append(orderState, order.orderState)
                .append(date, order.date)
                .append(address, order.address)
                .append(paymentConditions, order.paymentConditions)
                .append(orderPositionList, order.orderPositionList)
                .isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(user)
                .append(orderState)
                .append(date)
                .append(address)
                .append(paymentConditions)
                .append(orderPositionList)
                .toHashCode();
    }

    @Override
    public String toString(){
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", orderState='" + orderState + '\'' +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", paymentConditions='" + paymentConditions + '\'' +
                ", orderPositionList=" + orderPositionList +
                '}';
    }
}
