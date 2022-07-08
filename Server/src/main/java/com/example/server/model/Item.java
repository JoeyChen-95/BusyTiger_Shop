package com.example.server.model;

import com.example.server.model.ItemEnum.ItemStatus;
import com.example.server.model.ItemEnum.Tag;
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
