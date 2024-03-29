package com.busyfish.server.model;

import com.busyfish.server.model.UserEnum.Shipping;
import com.busyfish.server.model.OrderEnum.Courier;
import com.busyfish.server.model.OrderEnum.OrderStatus;
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
