package com.example.server.service;

import com.example.server.exception.ItemException.ErrorCode;
import com.example.server.exception.ItemException.ItemException;
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
    private UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public List<Item> selectAllItems() {
        return itemMapper.selectAllItems();
    }

    public Item selectItemById(Integer id) {
        return itemMapper.selectItemById(id);
    }

    public void insertItem(Item item) {
        if (selectItemById(item.getId()) != null) {
            throw new ItemException(ErrorCode.DUPLICATE_ITEM, "Item with this id already exists!");
        }
        if (userService.selectUserById(item.getSellerId()) == null) {
            throw new ItemException(ErrorCode.NO_EXISTING_SELLER, "The seller does not exist!");
        }

        itemMapper.insertItem(item.getId(), item.getSellerId(), item.getName(), item.getPrice(), item.getTag().toString(), item.getStatus().toString(), item.getDescription());
    }

    public void deleteItemById(Integer id) {
        if (selectItemById(id) == null) {
            throw new ItemException(ErrorCode.NO_EXISTING_ITEM, "Item with this id already exists!");
        }
        itemMapper.deleteItemById(id);
    }


    @Transactional
    public void updateItemStatus(Integer id, ItemStatus status) {
        if (selectItemById(id) == null) {
            throw new ItemException(ErrorCode.NO_EXISTING_ITEM, "Item with this id already exists!");
        }
        itemMapper.updateItemStatus(id, status.toString());
    }
}
