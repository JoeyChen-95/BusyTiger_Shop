package com.example.server.service;

import com.example.server.mapper.ItemMapper;
import com.example.server.model.Item;
import com.example.server.model.ItemEnum.ItemStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("itemService")
public class ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public List<Item> selectAllItems(){
        return itemMapper.selectAllItems();
    }

    public Item selectItemById(Integer id){
        return itemMapper.selectItemById(id);
    }

    public Item insertItem(Item item){
        itemMapper.insertItem(item.getId(), item.getSellerId(),item.getName(), item.getPrice(), item.getTag().toString(), item.getStatus().toString(), item.getDescription());
        return item;
    }

    public Item deleteItemById(Integer id){
        Item itemDeleted=selectItemById(id);
        itemMapper.deleteItemById(id);
        return itemDeleted;
    }




    @Transactional
    public Item updateItemStatus(Integer id, ItemStatus status){
        itemMapper.updateItemStatus(id,status.toString());
        return selectItemById(id);
    }
}
