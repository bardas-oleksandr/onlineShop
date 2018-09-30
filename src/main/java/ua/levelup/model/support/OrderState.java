package ua.levelup.model.support;

public enum OrderState {
    REGISTERED, PAYED, CANCELED;

    public static OrderState getOrderState(int stateIndex){
        switch(stateIndex){
            case 0:
                return REGISTERED;
            case 1:
                return PAYED;
            case 2:
                return CANCELED;
            default:
                throw new IllegalArgumentException("Illegal order state: " + stateIndex);
        }
    }
}
