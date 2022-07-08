package com.example.server.controller;

import com.example.server.model.Item;
import com.example.server.model.ItemEnum.ItemStatus;
import com.example.server.model.ItemEnum.Tag;
import com.example.server.service.ItemService;
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
    public Item selectBookById(@RequestParam("itemId") Integer itemId) {
        return itemService.selectItemById(itemId);
    }

    @DeleteMapping(value = "/deleteItemById")
    public Item deleteItemById(@RequestParam("deleteItemById") Integer id) {
        return itemService.deleteItemById(id);
    }

    @PutMapping(value = "updateItemStatus")
    public Item updateItemStatus(@RequestParam("itemId") Integer itemId,
                                 @RequestParam("newStatus") ItemStatus status) {
        return itemService.updateItemStatus(itemId, status);
    }

    @PostMapping(value = "/insertItem")
    public Item insertItem(@RequestParam("itemId") Integer itemId,
                           @RequestParam("sellerId") Integer sellerId,
                           @RequestParam("itemName") String itemName,
                           @RequestParam("itemPrice") Integer itemPrice,
                           @RequestParam("itemTag") Tag itemTag,
                           @RequestParam("itemStatus") ItemStatus itemStatus,
                           @RequestParam("itemDesc") String itemDesc) {
        Item item = new Item();
        item.setId(itemId);
        item.setSellerId(sellerId);
        item.setName(itemName);
        item.setPrice(itemPrice);
        item.setTag(itemTag);
        item.setStatus(itemStatus);
        item.setDescription(itemDesc);
        return itemService.insertItem(item);
    }
}
