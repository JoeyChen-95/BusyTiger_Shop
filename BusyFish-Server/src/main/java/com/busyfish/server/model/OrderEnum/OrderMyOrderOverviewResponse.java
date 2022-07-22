package com.busyfish.server.model.OrderEnum;

import com.busyfish.server.model.UserEnum.Shipping;
import lombok.Data;

import java.sql.Timestamp;

/**
 * author:junjian.chen
 * 下午3:13
 */
@Data
public class OrderMyOrderOverviewResponse {
    String id;
    Integer buyerId;
    Integer itemId;
    String itemName;
    OrderStatus status;
    Courier courier;
    String trackingNo;
    Integer courierFee;
    String shippingAddressName;
    String shippingAddressPhone;
    String shippingAddressAddress;
    Timestamp createTime;
    Timestamp completeTime;

    public OrderMyOrderOverviewResponse() {
    }

    public OrderMyOrderOverviewResponse(String id, Integer buyerId, Integer itemId, String itemName, OrderStatus status, Courier courier, String trackingNo, Integer courierFee, String shippingAddressName, String shippingAddressPhone, String shippingAddressAddress, Timestamp createTime, Timestamp completeTime) {
        this.id = id;
        this.buyerId = buyerId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.status = status;
        this.courier = courier;
        this.trackingNo = trackingNo;
        this.courierFee = courierFee;
        this.shippingAddressName = shippingAddressName;
        this.shippingAddressPhone = shippingAddressPhone;
        this.shippingAddressAddress = shippingAddressAddress;
        this.createTime = createTime;
        this.completeTime = completeTime;
    }
}
