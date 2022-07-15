package com.busyfish.server.exception.OrderException;

import com.busyfish.server.exception.BaseException;

public class OrderException extends BaseException {

    public OrderException(int errorCode, String message) {
        super(errorCode, message);
    }
}
