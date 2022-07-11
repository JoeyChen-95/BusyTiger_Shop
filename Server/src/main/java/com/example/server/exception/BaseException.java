package com.example.server.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BaseException extends RuntimeException{
    /**
     * Error Code
     */
    private int errorCode;

    /**
     * Construct a base exception
     * @param errorCode
     * @param message
     */
    public BaseException(int errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Construct a base exception
     * @param message
     * @param cause
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
