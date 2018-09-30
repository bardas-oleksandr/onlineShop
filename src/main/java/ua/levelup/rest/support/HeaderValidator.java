package ua.levelup.rest.support;

import ua.levelup.exception.RestException;

import javax.ws.rs.core.MediaType;

public enum HeaderValidator {
    ;

    public static void validateHeaders(String accept, String contentType) {
        if (!accept.contains(MediaType.APPLICATION_JSON) ||
                !contentType.contains(MediaType.APPLICATION_JSON)) {
            throw new RestException(415);   //Unsupported media type
        }
    }
}
