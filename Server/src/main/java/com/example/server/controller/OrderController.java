package com.example.server.controller;

import com.example.server.exception.OrderException.OrderException;
import com.example.server.model.Order;
import com.example.server.model.OrderEnum.Courier;
import com.example.server.model.OrderEnum.OrderStatus;
import com.example.server.service.OrderService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/selectAllOrders")
    public List<Order> selectAllOrders() {
        return orderService.selectAllOrders();
    }

    @GetMapping(value = "/selectOrderById")
    public Order selectOrderById(@RequestParam("orderId") String orderId) {
        return orderService.selectOrderById(orderId);
    }

    @PostMapping(value = "/buy")
    public ResponseBody buy(@RequestParam("buyerId") Integer buyerId,
                            @RequestParam("itemId") Integer itemId,
                            @RequestParam("shippingCode") String shippingKey,
                            @RequestParam(value = "comment", required = false) String comment) {
        String orderId;
        try {
            orderId = orderService.buy(buyerId, itemId, shippingKey, comment).getId();
        } catch (OrderException e) {
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, String.format("Order %s is created successfully!", orderId));
    }

    @PutMapping(value = "/shipOrder")
    public ResponseBody shipOrder(@RequestParam("orderId") String orderId,
                                  @RequestParam("courier") Courier courier,
                                  @RequestParam("trackingNo") String trackingNo,
                                  @RequestParam("courierFee") Integer courierFee) {
        try {
            orderService.shipOrder(orderId, courier, trackingNo, courierFee);
        } catch (OrderException e) {
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, String.format("Order %s is shipped successfully!", orderId));
    }

    @PutMapping(value = "/updateShippingInfo")
    public ResponseBody updateShippingInfo(@RequestParam("orderId") String orderId,
                                           @RequestParam("courier") Courier courier,
                                           @RequestParam("trackingNo") String trackingNo,
                                           @RequestParam("courierFee") Integer courierFee) {
        List<Order> data = new ArrayList<>();
        Order order = new Order();
        try {
            order = orderService.updateShippingInfo(orderId, courier, trackingNo, courierFee);
            data.add(order);
        } catch (OrderException e) {
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, String.format("Shipping Information of order %s is updated successfully!", orderId));
    }

    @PutMapping(value = "/updateOrderStatus")
    public ResponseBody updateOrderStatus(@RequestParam("orderId") String orderId,
                                          @RequestParam("status") OrderStatus status) {
        List<Order> data = new ArrayList<>();
        Order order = new Order();
        try {
            order = orderService.updateOrderStatus(orderId, status);
            data.add(order);
        } catch (OrderException e) {
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, "Order status is updated successfully!");
    }

    @DeleteMapping(value = "/deleteOrderById")
    public ResponseBody deleteOrderById(@RequestParam("orderId") String orderId){
        try{
            orderService.deleteOrderById(orderId);
        }catch (OrderException e){
            return new ResponseBody(500,e.getMessage());
        }
        return new ResponseBody(200,"Order is deleted successfully!");
    }
}
