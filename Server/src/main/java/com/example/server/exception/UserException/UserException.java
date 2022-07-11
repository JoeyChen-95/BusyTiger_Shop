package com.example.server.exception.UserException;

import com.example.server.exception.BaseException;

public class UserException extends BaseException {
    public UserException(int errorCode, String message) {
        super(errorCode, message);
    }
}
