package com.example.server.service;

import com.example.server.mapper.OrderMapper;
import com.example.server.model.ItemEnum.ItemStatus;
import com.example.server.model.Order;
import com.example.server.model.UserEnum.Shipping;
import com.example.server.util.ShippingCodeUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("orderService")
public class OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ItemService itemService;


    public List<Order> selectAllOrders(){
        return orderMapper.selectAllOrders();
    }

    public Order selectOrderById(Integer id){
        return orderMapper.selectOrderById(id);
    }

    @Transactional
    public Order buy(Integer buyerId, Integer itemId, String shippingKey,String comment){
        //generate random UUID as order's ID
        String orderId= UUID.randomUUID().toString().replace("-","");
        orderMapper.buy(orderId,buyerId, itemId,comment);
        //Save the information of shipping address of the order to redis
        String name= (String) redisTemplate.boundHashOps(shippingKey).get("name");
        String phone= (String) redisTemplate.boundHashOps(shippingKey).get("phone");
        String address= (String) redisTemplate.boundHashOps(shippingKey).get("address");
        Map<String,String> mapInfo=new HashMap<>();
        mapInfo.put("name",name);
        mapInfo.put("phone",phone);
        mapInfo.put("address",address);
        redisTemplate.opsForHash().putAll(ShippingCodeUtils.generateOrderShippingKey(orderId),mapInfo);
        //update the status of the item
        itemService.updateItemStatus(itemId, ItemStatus.SOLD);
        //return the order
        Order order=new Order();
        order.setBuyerId(buyerId);
        order.setId(orderId);
        order.setItemId(itemId);
        order.setComment(comment);
        Shipping shipping=new Shipping();
        shipping.setName(name);
        shipping.setPhone(phone);
        shipping.setAddress(address);
        order.setShippingAddress(shipping);
        return order;
    }


}
