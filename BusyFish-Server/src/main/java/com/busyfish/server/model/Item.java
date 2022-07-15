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
}
