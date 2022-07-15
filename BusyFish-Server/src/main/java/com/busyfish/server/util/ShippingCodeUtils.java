package com.busyfish.server.util;

public class ShippingCodeUtils {

    public static String generateSingleShippingKey(Integer userId, Integer shippingCode) {
        String key = "user_" + userId + "_shipping_" + shippingCode;
        return key;
    }

    public static String generateOrderShippingKey(String orderId) {
        String key = "order_" + orderId + "_shipping";
        return key;
    }

}
