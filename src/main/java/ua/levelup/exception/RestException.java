package ua.levelup.exception;

public class RestException extends RuntimeException {
    private int httpStatus;

    public RestException(int httpStatus){
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus(){
        return httpStatus;
    }
}
