package com.busyfish.server.service;

import com.busyfish.server.exception.ItemException.ErrorCode;
import com.busyfish.server.exception.ItemException.ItemException;
import com.busyfish.server.mapper.ItemMapper;
import com.busyfish.server.model.Item;
import com.busyfish.server.model.ItemEnum.ItemOverviewResponse;
import com.busyfish.server.model.ItemEnum.ItemStatus;
import com.busyfish.server.model.ItemEnum.Tag;
import com.busyfish.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
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
    public List<Item> selectItemByStatus(ItemStatus status){

        return itemMapper.selectItemByStatus(status.toString());
    }

    public List<ItemOverviewResponse> queryItem(Integer id, Integer sellerId, String name, Integer maxPrice, Integer minPrice, Tag tag, ItemStatus status){
        List<Item> itemList= itemMapper.queryItem(id,sellerId,name,maxPrice,minPrice, tag==null?null: tag.name(), status==null?null: status.name());
        List<ItemOverviewResponse> responseList=new ArrayList<>();
        for(Item item:itemList){
            User user =userService.selectUserById(item.getSellerId());
            if(user!=null){
                responseList.add(new ItemOverviewResponse(item.getId(),item.getSellerId(),user.getUsername(), item.getName(), item.getPrice(),item.getTag(),item.getStatus(),item.getDescription()));
            }
        }
        return responseList;
    }

    public void insertItem(Item item) {
//        if (selectItemById(item.getId()) != null) {
//            throw new ItemException(ErrorCode.DUPLICATE_ITEM, "Item with this id already exists!");
//        }
        if (userService.selectUserById(item.getSellerId()) == null) {
            throw new ItemException(ErrorCode.NO_EXISTING_SELLER, "The seller does not exist!");
        }
        try{
            itemMapper.insertItem(item.getSellerId(), item.getName(), item.getPrice(), item.getTag().toString(), item.getStatus().toString(), item.getDescription());
        }catch (Exception e){
            throw new ItemException(ErrorCode.ITEM_CREATE_FAILURE, "Fail to create item: "+e.getMessage());
        }

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

    @Transactional
    public void updateItem(Item item){
        if (userService.selectUserById(item.getSellerId()) == null) {
            throw new ItemException(ErrorCode.NO_EXISTING_SELLER, "The seller does not exist!");
        }
        try{
            itemMapper.updateItem(item.getId(),item.getSellerId(), item.getName(), item.getPrice(), item.getTag().toString(), item.getStatus().toString(), item.getDescription());
        }catch (Exception e){
            throw new ItemException(ErrorCode.ITEM_CREATE_FAILURE, "Fail to update item: "+e.getMessage());
        }
    }


}
