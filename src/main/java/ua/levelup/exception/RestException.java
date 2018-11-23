package ua.levelup.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestException extends RuntimeException {

    private HttpStatus httpStatus;

    public RestException(HttpStatus httpStatus){
        this.httpStatus = httpStatus;
    }
}
