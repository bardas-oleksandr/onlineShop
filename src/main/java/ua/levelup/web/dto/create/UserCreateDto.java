package ua.levelup.web.dto.create;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class UserCreateDto implements Serializable {

    private static final long serialVersionUID = 6331639670419471040L;

    private String userName;
    private String password;
    private String email;

    public UserCreateDto(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
}