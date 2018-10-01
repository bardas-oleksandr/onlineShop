package ua.levelup.model;

import lombok.*;
import ua.levelup.model.support.OrderState;
import ua.levelup.model.support.PaymentConditions;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Order implements Serializable {

    private static final long serialVersionUID = -8048642462864555907L;

    private int id;
    private User user;
    private OrderState orderState;
    private Timestamp date;
    private String address;
    private PaymentConditions paymentConditions;
    private List<OrderPosition> orderPositionList;

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
}