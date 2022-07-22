package com.busyfish.server.controller;

import com.busyfish.server.exception.ItemException.ItemException;
import com.busyfish.server.model.Item;
import com.busyfish.server.model.ItemEnum.ItemOverviewResponse;
import com.busyfish.server.model.ItemEnum.ItemStatus;
import com.busyfish.server.model.ItemEnum.Tag;
import com.busyfish.server.service.ItemService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/selectAllItems")
    public List<Item> selectAllItems() {
        return itemService.selectAllItems();
    }

    @GetMapping(value = "/selectItemById")
    public Item selectItemById(@RequestParam("itemId") Integer itemId) {
        return itemService.selectItemById(itemId);
    }

    @GetMapping(value = "/selectAllActiveItem")
    public List<ItemOverviewResponse> selectAllActiveItem(){
        return itemService.queryItem(null,null,null,null,null,null,ItemStatus.ACTIVE);
    }

    @DeleteMapping(value = "/deleteItemById")
    public ResponseBody deleteItemById(@RequestParam("id") Integer id) {
        try {
            itemService.deleteItemById(id);
        } catch (ItemException e) {
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, "Delete item successfully");
    }

    @PutMapping(value = "updateItemStatus")
    public ResponseBody updateItemStatus(@RequestParam("itemId") Integer itemId,
                                         @RequestParam("newStatus") ItemStatus newStatus) {
        try {
            itemService.updateItemStatus(itemId, newStatus);
        } catch (ItemException e) {
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, "Update item's status successfully");
    }

    @PostMapping(value = "/insertItem")
    public ResponseBody insertItem(@RequestParam("sellerId") Integer sellerId,
                                   @RequestParam("itemName") String itemName,
                                   @RequestParam("itemPrice") Integer itemPrice,
                                   @RequestParam("itemTag") Tag itemTag,
                                   @RequestParam("itemStatus") ItemStatus itemStatus,
                                   @RequestParam("itemDesc") String itemDesc) {
        Item item = new Item();
        item.setSellerId(sellerId);
        item.setName(itemName);
        item.setPrice(itemPrice);
        item.setTag(itemTag);
        item.setStatus(itemStatus);
        item.setDescription(itemDesc);
        try {
            itemService.insertItem(item);
        } catch (ItemException e) {
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, "Add item successfully");
    }

    @PutMapping(value = "/updateItem")
    public ResponseBody updateItem(@RequestParam("id") Integer id,
                                   @RequestParam("sellerId") Integer sellerId,
                                   @RequestParam("itemName") String itemName,
                                   @RequestParam("itemPrice") Integer itemPrice,
                                   @RequestParam("itemTag") Tag itemTag,
                                   @RequestParam("itemStatus") ItemStatus itemStatus,
                                   @RequestParam("itemDesc") String itemDesc){
        try{
            Item item=new Item(id,sellerId,itemName,itemPrice,itemTag,itemStatus,itemDesc);
            itemService.updateItem(item);
        }catch(ItemException e){
            return new ResponseBody(500, e.getMessage());
        }

        return new ResponseBody(200, "Update item successfully");
    }
}
