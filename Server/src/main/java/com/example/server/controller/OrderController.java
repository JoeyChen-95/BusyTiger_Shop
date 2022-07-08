package com.example.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.server.model.Item;
import com.example.server.model.Order;
import com.example.server.model.User;
import com.example.server.model.UserEnum.Shipping;
import com.example.server.model.UserEnum.UserMemberShip;
import com.example.server.service.OrderService;
import com.example.server.service.UserService;
import com.example.server.util.ShippingCodeUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/selectAllOrders")
    public List<Order> selectAllOrders(){
        return orderService.selectAllOrders();
    }

    @GetMapping(value = "/selectOrderById")
    public Order selectOrderById(@RequestParam("orderId") Integer orderId){
        return orderService.selectOrderById(orderId);
    }

    @PostMapping(value = "/buy")
    public Order buy(@RequestParam("buyerId") Integer buyerId,
                     @RequestParam("itemId") Integer itemId,
                     @RequestParam("shippingCode") String shippingKey,
                     @RequestParam(value = "comment",required = false) String comment){
        return  orderService.buy(buyerId,itemId,shippingKey,comment);
    }
}
