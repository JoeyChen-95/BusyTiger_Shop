package com.example.server.service;

import com.example.server.mapper.ItemMapper;
import com.example.server.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("bookService")
public class ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public List<Item> selectAllBooks(){
        return itemMapper.selectAllItems();
    }

    public Item selectBookById(Integer id){
        return itemMapper.selectItemById(id);
    }

    public Item insertItem(Item item){
        itemMapper.insertItem(item.getId(), item.getName(), item.getPrice(), item.getTag().toString(), item.getStatus().toString(), item.getDescription());
        return item;
    }

    public void redisAddBook(Item item){
        Map<String,String> map=new HashMap<>();
        map.put("id",item.getId().toString());
        map.put("name",item.getName());
        map.put("description",item.getDescription());
        map.put("tag",item.getTag().toString());
        map.put("status",item.getStatus().toString());
        map.put("price", item.getPrice().toString());
        redisTemplate.opsForHash().putAll("item_"+item.getId().toString(),map);

    }



}
