package ua.levelup.web.dto.create;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 *
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class UserRegisterCreateDto implements Serializable {

    private static final long serialVersionUID = 6331639670419471040L;

    @Size(max = 20, message = "unacceptable_username_length")
    @NotEmpty(message = "empty_username")
    private String userName;

    @Size(min = 4,max = 20, message = "unacceptable_password_length")
    @NotNull(message = "empty_password")
    private String password;

    @Email(message = "wrong_email_format")
    @NotEmpty(message = "empty_email")
    private String email;

    @Min(value = 0, message = "unexpected_user_state")
    @Max(value = 2, message = "unexpected_user_state")
    private int userStateIndex;

    public UserRegisterCreateDto(String userName, String password, String email
            , int userStateIndex) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.userStateIndex = userStateIndex;
    }
}