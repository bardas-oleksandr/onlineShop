package ua.levelup.converter.fromdto;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.Credentials;
import ua.levelup.web.dto.create.CredentialsCreateDto;

/**
 *
 */
@Component("credentialsCreateDtoConverter")
public class CredentialsCreateDtoConverter
        implements Converter<CredentialsCreateDto, Credentials> {

    @Override
    public Credentials convert(@NonNull CredentialsCreateDto credentialsCreateDto) {
        Credentials credentials = new Credentials();
        credentials.setEmail(credentialsCreateDto.getEmail());
        credentials.setPassword(credentialsCreateDto.getPassword());
        return credentials;
    }
}
