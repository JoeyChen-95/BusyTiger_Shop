package com.example.server.exception.OrderException;

import com.example.server.exception.BaseException;

public class OrderException extends BaseException {

    public OrderException(int errorCode, String message) {
        super(errorCode, message);
    }
}
