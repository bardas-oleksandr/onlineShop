package ua.levelup.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Credentials implements Serializable {

    private static final long serialVersionUID = -2941909623954326175L;

    private String password;
    private String email;

    public Credentials(String password, String email) {
        this.password = password;
        this.email = email;
    }
}
