package com.busyfish.server;

import com.busyfish.server.model.Item;
import com.busyfish.server.model.ItemEnum.ItemStatus;
import com.busyfish.server.model.ItemEnum.Tag;
import com.busyfish.server.model.OrderEnum.Courier;
import com.busyfish.server.model.User;
import com.busyfish.server.model.UserEnum.UserMemberShip;
import com.busyfish.server.service.ItemService;
import com.busyfish.server.service.OrderService;
import com.busyfish.server.service.PrimeCollectionService;
import com.busyfish.server.service.UserService;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.List;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    ItemService itemService;
    @Autowired
    PrimeCollectionService primeCollectionService;

    @Test
    void contextLoads() {
    }

//    @Test
//    void getShippingAddressTest(){
//        userService.selectAllShippingAddressFromUser(1);
//    }
//
//    @Test
//    void loginTest(){
//        System.out.println(userService.login("Junjian123","11111"));
//    }
//
//    @Test
//    void updateItemStatusTest(){
//        itemService.updateItemStatus(1, ItemStatus.SOLD);
//        itemService.updateItemStatus(2, ItemStatus.PRIVATE);
//    }
//
//    @Test
//    void deleteItemTest(){
//        itemService.deleteItemById(2);
//    }
//
//    @Test
//    void shipOrderTest(){
//        orderService.shipOrder("985a3bf1281d4ca8b3c634bd2cd1286b", Courier.Canada_Post,"CA1231231555",20);
//
//    }
//
//    @Test
//    void selectAllOrder(){
//        orderService.selectAllOrders();
//    }
//
//    @Test
//    void testGetShipping(){
//        System.out.println(orderService.selectShippingAddressByOrderId("2330f2ae6c30465b814c9b793281d883"));
//    }
//
////    @Test
////    void testGetSessionId(){
////        HttpServletRequest httpServletRequest=new Request()
////        System.out.println(userService.getSessionId(httpServletRequest));
////    }
////
////    @Test
////    void testSetSessionId(){
////        HttpServletRequest httpServletRequest=new MockHttpServletRequest();
////        User user=new User();
////        user.setUsername("lkwrgkjoirgjirg");
////        user.setId(1010101);
////        System.out.println(userService.setSessionId(httpServletRequest,user));
////    }
//
//    @Test
//    void testQueryItem(){
////        List<Item> itemList=itemService.queryItem(null,null,"1",null,null, null,null);
////        System.out.println(itemList);
//    }
//
//    @Test
//    void selectOrdersBySeller(){
//        System.out.println(orderService.selectOrdersBySellerId(1));
//    }

//    @Test
//    void testQueryUser(){
//        for(User u:userService.queryUser(null,null,"15280071872",null, null)){
//            System.out.println(u);
//        }
//    }

    @Test
    void testAddCollection(){
        primeCollectionService.createPrimeCollection("NBA");
    }

    @Test
    void testAddcollection(){
        primeCollectionService.addItemToPrimeCollection("NBA",115);
        primeCollectionService.addItemToPrimeCollection("NBA",116);
        primeCollectionService.addItemToPrimeCollection("NBA",126);
    }

    @Test
    void testDeleteItemFromCollection(){
        primeCollectionService.deleteItemFromPrimeCollection("WNBA",120);
    }

    @Test
    void testDeleteCollection(){
        primeCollectionService.deletePrimeCollection("WNBA");
    }

    @Test
    void testGetAllFromCollection(){
        for(Item i: primeCollectionService.selectAllItemsOfPrimeCollection("A")){
            System.out.println(i);
        }
    }
}
