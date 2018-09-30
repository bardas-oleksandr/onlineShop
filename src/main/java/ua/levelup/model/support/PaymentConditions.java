package ua.levelup.model.support;

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
