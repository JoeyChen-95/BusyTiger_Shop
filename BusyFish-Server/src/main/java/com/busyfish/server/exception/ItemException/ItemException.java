package com.busyfish.server.exception.ItemException;

import com.busyfish.server.exception.BaseException;

public class ItemException extends BaseException {
    public ItemException(int errorCode, String message) {
        super(errorCode, message);
    }
}
