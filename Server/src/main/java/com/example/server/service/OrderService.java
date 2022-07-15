package com.example.server.service;

import com.example.server.exception.OrderException.ErrorCode;
import com.example.server.exception.OrderException.OrderException;
import com.example.server.exception.UserException.UserException;
import com.example.server.mapper.OrderMapper;
import com.example.server.model.ItemEnum.ItemStatus;
import com.example.server.model.Order;
import com.example.server.model.OrderEnum.Courier;
import com.example.server.model.OrderEnum.OrderStatus;
import com.example.server.model.UserEnum.Shipping;
import com.example.server.model.UserEnum.UserMemberShip;
import com.example.server.util.ShippingCodeUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.OrderUtils;
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

    @Autowired
    UserService userService;


    public List<Order> selectAllOrders() {
        return orderMapper.selectAllOrders();
    }

    public Order selectOrderById(String id) {
        return orderMapper.selectOrderById(id);
    }

    /**
     * A Buyer buys an item and creates an order. The item's status will be set to SOLD
     *
     * @param buyerId
     * @param itemId
     * @param shippingKey
     * @param comment
     * @return
     */
    @Transactional
    public Order buy(Integer buyerId, Integer itemId, String shippingKey, String comment) {
        //check correctness of information of the order
        if (userService.selectUserById(buyerId) == null) {
            throw new OrderException(ErrorCode.NO_EXISTING_BUYER, "Fail to create the order: the buyer does not exist!");
        }
        if (userService.selectUserById(buyerId).getMemberShip().equals(UserMemberShip.BANNED) || userService.selectUserById(buyerId).getMemberShip().equals(UserMemberShip.FROZEN)) {
            throw new OrderException(ErrorCode.LOCKED_ACCOUNT_BUYER, "Fail to create the order: the buyer's account is locked!");
        }
        if (itemService.selectItemById(itemId) == null) {
            throw new OrderException(ErrorCode.NO_EXISTING_PURCHASED_ITEM, "Fail to create the order: the item does not exist!");
        }
        if (!itemService.selectItemById(itemId).getStatus().equals(ItemStatus.ACTIVE)) {
            throw new OrderException(ErrorCode.PURCHASED_ITEM_STATUS_EXCEPTION, "Fail to create the order: the exception status of purchased item!");
        }
        if (!redisTemplate.hasKey(shippingKey)) {
            throw new OrderException(ErrorCode.NO_EXISTING_SHIPPING_ADDRESS, "Fail to create the order: the shipping address does not exist!");
        }
        //generate random UUID as order's ID
        String orderId = UUID.randomUUID().toString().replace("-", "");
        orderMapper.buy(orderId, buyerId, itemId, comment);
        //Save the information of shipping address of the order to redis
        String name = (String) redisTemplate.boundHashOps(shippingKey).get("name");
        String phone = (String) redisTemplate.boundHashOps(shippingKey).get("phone");
        String address = (String) redisTemplate.boundHashOps(shippingKey).get("address");
        Map<String, String> mapInfo = new HashMap<>();
        mapInfo.put("name", name);
        mapInfo.put("phone", phone);
        mapInfo.put("address", address);
        redisTemplate.opsForHash().putAll(ShippingCodeUtils.generateOrderShippingKey(orderId), mapInfo);
        //update the status of the item
        itemService.updateItemStatus(itemId, ItemStatus.SOLD);
        //return the order
        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setId(orderId);
        order.setItemId(itemId);
        order.setComment(comment);
        Shipping shipping = new Shipping();
        shipping.setName(name);
        shipping.setPhone(phone);
        shipping.setAddress(address);
        order.setShippingAddress(shipping);
        return order;
    }

    @Transactional
    public Order shipOrder(String id, Courier courier, String trackingNo, Integer courierFee) {
        if (selectOrderById(id) == null) {
            throw new OrderException(ErrorCode.NO_EXISTING_ORDER, "Fail to ship: the order does not exist!");
        }
        if (courierFee < 0) {
            throw new OrderException(ErrorCode.MINUS_COURIER_FEE, "Courier fee cannot be lower than 0!");
        }
        updateShippingInfo(id, courier, trackingNo, courierFee);
        updateOrderStatus(id, OrderStatus.SHIPPED);
        return selectOrderById(id);
    }

    @Transactional
    public Order updateShippingInfo(String id, Courier courier, String trackingNo, Integer courierFee) {
        if (selectOrderById(id) == null) {
            throw new OrderException(ErrorCode.NO_EXISTING_ORDER, "Fail to ship: the order does not exist!");
        }
        if (courierFee < 0) {
            throw new OrderException(ErrorCode.MINUS_COURIER_FEE, "Courier fee cannot be lower than 0!");
        }
        orderMapper.updateShippingInfo(id, courier.name(), trackingNo, courierFee);
        return selectOrderById(id);
    }

    @Transactional
    public Order updateOrderStatus(String id, OrderStatus status) {
        if (selectOrderById(id) == null) {
            throw new OrderException(ErrorCode.NO_EXISTING_ORDER, "Fail to ship: the order does not exist!");
        }
        orderMapper.updateOrderStatus(id, status.name());
        return selectOrderById(id);
    }

    @Transactional
    public Order deleteOrderById(String id){
        Order order=selectOrderById(id);
        if(order == null){
            throw new OrderException(ErrorCode.NO_EXISTING_ORDER, "Fail to delete: the order does not exist!");
        }
        redisTemplate.delete(ShippingCodeUtils.generateOrderShippingKey(id));
        orderMapper.deleteOrderById(id);
        return order;
    }


}
