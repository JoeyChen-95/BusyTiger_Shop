package com.example.server;

import com.example.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void getShippingAddressTest(){
        userService.selectAllShippingAddressFromUser(1);
    }

    @Test
    void loginTest(){
        System.out.println(userService.login("Junjian123","11111"));
    }

}
