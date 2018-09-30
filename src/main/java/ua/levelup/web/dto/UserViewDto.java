package ua.levelup.web.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "userViewDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserViewDto implements Serializable {

    private static final long serialVersionUID = -5411991248978416452L;

    private int id;
    private String userName;
    private String email;
    private int state;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getState() {
        return state;
    }

    public void setState(int stateIndex) {
        this.state = stateIndex;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(userName)
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
        UserViewDto user = (UserViewDto) other;
        return new EqualsBuilder()
                .append(id, user.id)
                .append(userName, user.userName)
                .append(email, user.email)
                .append(state, user.state)
                .isEquals();
    }
}
