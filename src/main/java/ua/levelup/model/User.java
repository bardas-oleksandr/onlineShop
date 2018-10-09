package ua.levelup.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User implements Serializable {

    private static final long serialVersionUID = -1153406954451373752L;

    private int id;
    private String userName;
    private String password;
    private String email;
    private UserState userState;

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public User(String userName, String password, String email, int userStateIndex) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.userState = UserState.getUserState(userStateIndex);
    }

    public void setUserState(int stateIndex) {
        this.userState = UserState.getUserState(stateIndex);
    }

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
}