package ua.levelup.web.dto;

import ua.levelup.model.support.UserState;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "userCreateDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserCreateDto implements Serializable {

    private static final long serialVersionUID = -6144709737647985570L;

    private String userName;
    private String password;
    private String email;
    private UserState state;

    public UserCreateDto(){}

    public UserCreateDto(String userName, String password, String email, UserState state) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.state = state;
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

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userName)
                .append(password)
                .append(email)
                .append(state)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        UserCreateDto user = (UserCreateDto) other;
        return new EqualsBuilder()
                .append(userName, user.userName)
                .append(password, user.password)
                .append(email, user.email)
                .append(state, user.state)
                .isEquals();
    }
}
