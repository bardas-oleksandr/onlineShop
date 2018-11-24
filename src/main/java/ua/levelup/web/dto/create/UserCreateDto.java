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
public class UserCreateDto implements Serializable {

    private static final long serialVersionUID = 247754229132006935L;

    @Size(max = 20, message = "unacceptable_username_length")
    @NotEmpty(message = "empty_username")
    private String userName;

    @Email(message = "wrong_email_format")
    @NotEmpty(message = "empty_email")
    private String email;

    @Min(value = 0, message = "unexpected_user_state")
    @Max(value = 2, message = "unexpected_user_state")
    private int userStateIndex;

    public UserCreateDto(String userName, String email, int userStateIndex) {
        this.userName = userName;
        this.email = email;
        this.userStateIndex = userStateIndex;
    }
}
