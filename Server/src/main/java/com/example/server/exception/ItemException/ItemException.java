package com.example.server.exception.ItemException;

import com.example.server.exception.BaseException;

public class ItemException extends BaseException {
    public ItemException(int errorCode, String message) {
        super(errorCode, message);
    }
}
