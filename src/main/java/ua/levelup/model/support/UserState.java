package ua.levelup.model.support;

public enum UserState {
    ADMIN, ACTIVE, BLOCKED;

    public static UserState getUserState(int stateIndex){
        switch(stateIndex){
            case 0:
                return ADMIN;
            case 1:
                return ACTIVE;
            case 2:
                return BLOCKED;
            default:
                throw new IllegalArgumentException("Illegal user state: " + stateIndex);
        }
    }
}
