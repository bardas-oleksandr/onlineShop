package ua.levelup.model;

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

    private int id;
    private String userName;
    private String password;
    private String email;
    private UserState userState;

    public User() { }

    public User(String userName, String password, String email, int state) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.userState = UserState.getUserState(state);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserState getUserState() {
        return userState;
    }

    public void setUserState(int state) {
        this.userState = UserState.getUserState(state);
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
    public int hashCode(){
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(userName)
                .append(password)
                .append(email)
                .append(userState)
                .toHashCode();
    }

    @Override
    public String toString(){
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userState='" + userState + '\'' +
                '}';
    }
}
