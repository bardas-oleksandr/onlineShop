package ua.levelup.web.dto.view;

import lombok.*;

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
@NoArgsConstructor
public class UserViewDto implements Serializable {

    private static final long serialVersionUID = -5411991248978416452L;

    private int id;
    private String userName;
    private String email;
    private int state;
}