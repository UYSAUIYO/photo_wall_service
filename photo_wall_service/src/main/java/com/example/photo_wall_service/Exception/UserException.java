package com.example.photo_wall_service.Exception;

/**
 * 自定义异常
 */
public class UserException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserException() {
        super();
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Throwable cause) {
        super(cause);
    }
}
