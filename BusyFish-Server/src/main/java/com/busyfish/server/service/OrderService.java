package com.busyfish.server.service;

import com.busyfish.server.exception.OrderException.ErrorCode;
import com.busyfish.server.mapper.OrderMapper;
import com.busyfish.server.model.Item;
import com.busyfish.server.model.ItemEnum.ItemStatus;
import com.busyfish.server.model.OrderEnum.OrderMyOrderOverviewResponse;
import com.busyfish.server.model.User;
import com.busyfish.server.model.UserEnum.Shipping;
import com.busyfish.server.model.UserEnum.UserMemberShip;
import com.busyfish.server.util.ShippingCodeUtils;
import com.busyfish.server.exception.OrderException.OrderException;
import com.busyfish.server.model.Order;
import com.busyfish.server.model.OrderEnum.Courier;
import com.busyfish.server.model.OrderEnum.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        List<Order> orderList=orderMapper.selectAllOrders();
        for(Order o:orderList){
            Map orderMap = redisTemplate.boundHashOps(ShippingCodeUtils.generateOrderShippingKey(o.getId())).entries();
            Shipping shipping=new Shipping();
            shipping.setAddress( orderMap.get("address").toString());
            shipping.setName( orderMap.get("name").toString());
            shipping.setPhone( orderMap.get("phone").toString());
            o.setShippingAddress(shipping);
        }
        return orderMapper.selectAllOrders();
    }

    public Order selectOrderById(String id) {
        return orderMapper.selectOrderById(id);
    }

    public List<OrderMyOrderOverviewResponse> selectOrdersByBuyerId(Integer buyerId){
        User buyer=userService.selectUserById(buyerId);
        if(buyer==null){
            throw new OrderException(ErrorCode.NO_EXISTING_BUYER,"Buyer does not exist!");
        }
        try{
            List<Order> orderList=orderMapper.selectOrdersByBuyerId(buyerId);
            List<OrderMyOrderOverviewResponse> responseList=new ArrayList<>();
            for(Order order:orderList){
                Item item=itemService.selectItemById(order.getItemId());
                Shipping shipping=selectShippingAddressByOrderId(order.getId());
                User seller = null;
                if(item!=null){
                    seller=userService.selectUserById(item.getSellerId());
                }
                if(item!=null&&seller!=null){
                    responseList.add(new OrderMyOrderOverviewResponse(order.getId(), order.getBuyerId(), order.getItemId(), item.getName(), order.getStatus(),order.getCourier(), order.getTrackingNo(), order.getCourierFee(), shipping.getName(),shipping.getPhone(),shipping.getAddress(),order.getCreateTime(),order.getCompleteTime(),seller.getUsername(),buyer.getUsername()));
                }

            }
            return responseList;
        }catch (Exception e){
//            throw new OrderException(ErrorCode.ORDER_EXCEPTION,"Fail to find orders!");
            throw e;
        }

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

    @Transactional
    public Shipping selectShippingAddressByOrderId(String orderId){
        Map orderMap = redisTemplate.boundHashOps(ShippingCodeUtils.generateOrderShippingKey(orderId)).entries();
        Shipping shipping=new Shipping();
        shipping.setAddress( orderMap.get("address").toString());
        shipping.setName( orderMap.get("name").toString());
        shipping.setPhone( orderMap.get("phone").toString());
        return shipping;
    }

    @Transactional
    public List<OrderMyOrderOverviewResponse> selectOrdersBySellerId(Integer sellerId){
        User seller=userService.selectUserById(sellerId);
        if(seller==null){
            throw new OrderException(ErrorCode.NO_EXISTING_SELLER,"Seller does not exist!");
        }
        try{
            List<Order> orderList=orderMapper.selectOrdersBySellerId(sellerId);
            List<OrderMyOrderOverviewResponse> responseList=new ArrayList<>();
            for(Order order:orderList){
                Item item=itemService.selectItemById(order.getItemId());
                Shipping shipping=selectShippingAddressByOrderId(order.getId());
                User buyer=userService.selectUserById(order.getBuyerId());
                if(item!=null&&buyer!=null){
                    responseList.add(new OrderMyOrderOverviewResponse(order.getId(), order.getBuyerId(), order.getItemId(), item.getName(), order.getStatus(),order.getCourier(), order.getTrackingNo(), order.getCourierFee(), shipping.getName(),shipping.getPhone(),shipping.getAddress(),order.getCreateTime(),order.getCompleteTime(),seller.getUsername(), buyer.getUsername()));
                }

            }
            return responseList;

        }catch (Exception e){
            throw new OrderException(ErrorCode.ORDER_EXCEPTION,"Fail to find orders!");
        }

    }



}
