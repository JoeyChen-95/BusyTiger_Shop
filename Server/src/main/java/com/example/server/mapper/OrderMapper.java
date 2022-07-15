package com.example.server.mapper;

import com.example.server.model.Order;
import com.example.server.model.User;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> selectAllOrders();

    Order selectOrderById(String id);

    void buy(String id, Integer buyerId, Integer itemId, String comment);

    void updateShippingInfo(String id, String courier, String trackingNo, Integer courierFee);

    void updateOrderStatus(String id, String status);

    void deleteOrderById(String id);
}
