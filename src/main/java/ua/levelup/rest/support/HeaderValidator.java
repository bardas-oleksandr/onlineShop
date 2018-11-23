package ua.levelup.rest.support;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ua.levelup.exception.RestException;

import javax.ws.rs.core.MediaType;

@Component
public class HeaderValidator {

    public void validateHeaders(String accept, String contentType) {
        if (!accept.contains(MediaType.APPLICATION_JSON) &&
                !contentType.contains(MediaType.APPLICATION_JSON)) {
            throw new RestException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
    }
}
