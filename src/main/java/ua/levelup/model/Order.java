package ua.levelup.model;

import lombok.*;

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
    private String address;
    private Timestamp date;
    private boolean payed;
    private OrderState orderState;
    private PaymentConditions paymentConditions;
    private List<OrderPosition> orderPositionList;

    public Order(User user, String address, Timestamp date, boolean payed,
                 int orderStateIndex, int paymentConditionsIndex) {
        this.user = user;
        this.address = address;
        this.date = date;
        this.payed = payed;
        this.orderState = OrderState.getOrderState(orderStateIndex);
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

    public enum OrderState {
        REGISTERED, EXECUTED, CANCELED;

        public static OrderState getOrderState(int stateIndex){
            switch(stateIndex){
                case 0:
                    return REGISTERED;
                case 1:
                    return EXECUTED;
                case 2:
                    return CANCELED;
                default:
                    throw new IllegalArgumentException("Illegal order state: " + stateIndex);
            }
        }
    }

    public enum PaymentConditions {
        CASH, CARD;

        public static PaymentConditions getPaymentConditions(int conditionsIndex){
            switch(conditionsIndex){
                case 0:
                    return CASH;
                case 1:
                    return CARD;
                default:
                    throw new IllegalArgumentException("Illegal payment conditions state: " + conditionsIndex);
            }
        }
    }
}