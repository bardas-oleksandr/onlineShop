package ua.levelup.model;

import lombok.Getter;
import lombok.Setter;
import ua.levelup.model.support.UserState;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

    private static final long serialVersionUID = -1153406954451373752L;

    @Getter @Setter private int id;
    @Getter @Setter private String userName;
    @Getter @Setter private String password;
    @Getter @Setter private String email;
    @Getter private UserState userState;

    public User() {
    }

    public User(String userName, String password, String email, int stateIndex) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.userState = UserState.getUserState(stateIndex);
    }

    public void setUserState(int stateIndex) {
        this.userState = UserState.getUserState(stateIndex);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        User user = (User) other;
        return new EqualsBuilder()
                .append(id, user.id)
                .append(userName, user.userName)
                .append(password, user.password)
                .append(email, user.email)
                .append(userState, user.userState)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(userName)
                .append(password)
                .append(email)
                .append(userState)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userState='" + userState + '\'' +
                '}';
    }
}
