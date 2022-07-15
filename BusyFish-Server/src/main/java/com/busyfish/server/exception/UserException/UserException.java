package com.busyfish.server.exception.UserException;

import com.busyfish.server.exception.BaseException;

public class UserException extends BaseException {
    public UserException(int errorCode, String message) {
        super(errorCode, message);
    }
}
