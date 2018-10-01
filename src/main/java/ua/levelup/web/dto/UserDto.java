package ua.levelup.web.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "userViewDto")
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class UserDto implements Serializable {

    private static final long serialVersionUID = -5411991248978416452L;

    private int id;
    private String userName;
    private String email;
    private int state;
}