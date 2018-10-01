package ua.levelup.model;

import lombok.*;
import ua.levelup.model.support.UserState;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public User(String userName, String password, String email, int stateIndex) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.userState = UserState.getUserState(stateIndex);
    }

    public void setUserState(int stateIndex) {
        this.userState = UserState.getUserState(stateIndex);
    }
}