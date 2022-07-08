package com.example.server.model;

import com.example.server.model.OrderEnum.Courier;
import com.example.server.model.OrderEnum.OrderStatus;
import com.example.server.model.UserEnum.Shipping;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Order {
    String id;
    Integer buyerId;
    Integer itemId;
    OrderStatus status;
    Courier courier;
    String trackingNo;
    Integer courierFee;
    Shipping shippingAddress;
    Timestamp createTime;
    Timestamp completeTime;
    String comment;
}
