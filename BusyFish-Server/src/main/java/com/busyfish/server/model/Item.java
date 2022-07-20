package com.busyfish.server.model;

import com.busyfish.server.model.ItemEnum.ItemStatus;
import com.busyfish.server.model.ItemEnum.Tag;
import lombok.Data;

@Data
public class Item {
    Integer id;
    Integer sellerId;
    String name;
    Integer price;
    Tag tag;
    ItemStatus status;
    String description;

    public Item() {
    }
    public Item(Integer id, Integer sellerId, String name, Integer price, Tag tag, ItemStatus status, String description) {
        this.id = id;
        this.sellerId = sellerId;
        this.name = name;
        this.price = price;
        this.tag = tag;
        this.status = status;
        this.description = description;
    }
}
