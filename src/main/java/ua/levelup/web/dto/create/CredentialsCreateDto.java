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
public class CredentialsCreateDto implements Serializable {

    private static final long serialVersionUID = 3523935973502537231L;

    @Size(min = 6,max = 20, message = "unacceptable_password_length")
    @NotNull(message = "empty_password")
    private String password;

    @Email(message = "wrong_email_format")
    @NotEmpty(message = "empty_email")
    private String email;

    public CredentialsCreateDto(String password, String email) {
        this.password = password;
        this.email = email;
    }
}
