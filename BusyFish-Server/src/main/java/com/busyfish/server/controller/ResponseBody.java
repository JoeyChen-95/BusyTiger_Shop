package com.busyfish.server.controller;

import lombok.Data;

@Data
public class ResponseBody {
    public ResponseBody(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;
}


