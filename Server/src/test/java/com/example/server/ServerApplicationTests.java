package com.example.server;

import com.example.server.mapper.OrderMapper;
import com.example.server.model.ItemEnum.ItemStatus;
import com.example.server.service.ItemService;
import com.example.server.service.OrderService;
import com.example.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    ItemService itemService;

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

    @Test
    void updateItemStatusTest(){
        System.out.println(itemService.updateItemStatus(1, ItemStatus.SOLD));
        System.out.println(itemService.updateItemStatus(2, ItemStatus.PRIVATE));
    }

    @Test
    void deleteItemTest(){
        itemService.deleteItemById(2);
    }


}
