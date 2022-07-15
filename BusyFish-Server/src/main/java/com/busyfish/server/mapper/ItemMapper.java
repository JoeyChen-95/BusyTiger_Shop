package com.busyfish.server.mapper;

import com.busyfish.server.model.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    List<Item> selectAllItems();

    Item selectItemById(Integer id);

    void insertItem(Integer id, Integer sellerId, String name, Integer price, String tag, String status, String description);

    void updateItemStatus(Integer id, String status);

    void deleteItemById(Integer id);
}
