package ua.levelup.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.Properties;

@ControllerAdvice
public class ShopExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver {

    @Autowired
    private Properties messagesProperties;

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException e){
        return new ResponseEntity<>(messagesProperties.getProperty("UNSUPPORTED_MEDIA_TYPE"),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> handleMediaTypeNotAcceptable(HttpMediaTypeNotSupportedException e){
        return new ResponseEntity<>(messagesProperties.getProperty("NOT_ACCEPTABLE_MEDIA_TYPE"),
                HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<String> handleRestException(RestException e){
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
