package com.busyfish.server.model.ItemEnum;

import lombok.Data;

/**
 * author:junjian.chen
 * 下午5:24
 */
@Data
public class ItemOverviewResponse {
    Integer id;
    Integer sellerId;
    String sellerName;
    String name;
    Integer price;
    Tag tag;
    ItemStatus status;
    String description;

    public ItemOverviewResponse(){

    }

    public ItemOverviewResponse(Integer id, Integer sellerId, String sellerName, String name, Integer price, Tag tag, ItemStatus status, String description) {
        this.id = id;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.name = name;
        this.price = price;
        this.tag = tag;
        this.status = status;
        this.description = description;
    }
}
