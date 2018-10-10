package ua.levelup.web.dto.create;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class UserCreateDto implements Serializable {

    private static final long serialVersionUID = 6331639670419471040L;

    @Size(max = 20, message = "unacceptable_username_length")
    @NotEmpty(message = "empty_username")
    private String userName;

    @Size(min = 6,max = 20, message = "unacceptable_password_length")
    @NotNull(message = "empty_password")
    private String password;

    @Email(message = "wrong_email_format")
    @NotEmpty(message = "empty_email")
    private String email;

    public UserCreateDto(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
}