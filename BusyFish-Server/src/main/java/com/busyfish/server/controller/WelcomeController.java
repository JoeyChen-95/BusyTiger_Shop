package com.busyfish.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:junjian.chen
 * 下午4:06
 */
@RestController
@Slf4j
public class WelcomeController {
    @GetMapping("/")
    public String hello()
    {
        return "Welcome to BusyTiger Backend!";
    }
    @GetMapping("/hello")
    public String hello2()
    {
        return "Hello User, have a nice day.";
    }

}
