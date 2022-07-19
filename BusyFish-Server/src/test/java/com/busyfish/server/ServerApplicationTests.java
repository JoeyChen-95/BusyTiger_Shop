package com.busyfish.server;

import com.busyfish.server.model.ItemEnum.ItemStatus;
import com.busyfish.server.model.OrderEnum.Courier;
import com.busyfish.server.service.ItemService;
import com.busyfish.server.service.OrderService;
import com.busyfish.server.service.UserService;
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
        itemService.updateItemStatus(1, ItemStatus.SOLD);
        itemService.updateItemStatus(2, ItemStatus.PRIVATE);
    }

    @Test
    void deleteItemTest(){
        itemService.deleteItemById(2);
    }

    @Test
    void shipOrderTest(){
        orderService.shipOrder("985a3bf1281d4ca8b3c634bd2cd1286b", Courier.Canada_Post,"CA1231231555",20);

    }

    @Test
    void selectAllOrder(){
        orderService.selectAllOrders();
    }

    @Test
    void testGetShipping(){
        System.out.println(orderService.selectShippingAddressByOrderId("2330f2ae6c30465b814c9b793281d883"));
    }


}
