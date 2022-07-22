package com.busyfish.server.mapper;

import com.busyfish.server.model.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> selectAllOrders();

    Order selectOrderById(String id);

    List<Order> selectOrdersByBuyerId(Integer buyerId);

    void buy(String id, Integer buyerId, Integer itemId, String comment);

    void updateShippingInfo(String id, String courier, String trackingNo, Integer courierFee);

    void updateOrderStatus(String id, String status);

    void deleteOrderById(String id);

}
