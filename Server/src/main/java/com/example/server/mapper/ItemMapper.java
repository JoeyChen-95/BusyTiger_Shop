package com.example.server.mapper;

import com.example.server.model.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    List<Item> selectAllItems();
    Item selectItemById(Integer id);
    void insertItem(Integer id, String name, Integer price, String tag, String status, String description);

}
