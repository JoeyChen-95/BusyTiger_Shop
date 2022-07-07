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
    public List<Item> selectAllBooks(){
        return itemService.selectAllBooks();
    }

    @GetMapping(value = "/selectItemById")
    public Item selectBookById(@RequestParam("bookId") Integer bookId){
        return itemService.selectBookById(bookId);
    }

    @PostMapping(value = "/insertItem")
    public Item insertItem(@RequestParam("itemId") Integer itemId,
                           @RequestParam("itemName") String itemName,
                           @RequestParam("itemPrice") Integer itemPrice,
                           @RequestParam("itemTag") Tag itemTag,
                           @RequestParam("itemStatus") ItemStatus itemStatus,
                           @RequestParam("itemDesc") String itemDesc){
        Item item=new Item();
        item.setId(itemId);
        item.setName(itemName);
        item.setPrice(itemPrice);
        item.setTag(itemTag);
        item.setStatus(itemStatus);
        item.setDescription(itemDesc);
        return itemService.insertItem(item);
    }
    @PostMapping(value = "/redisAddItem")
    public void redisAddBook(@RequestParam("itemId") Integer itemId,
                             @RequestParam("itemName") String itemName,
                             @RequestParam("itemPrice") Integer itemPrice,
                             @RequestParam("itemTag") Tag itemTag,
                             @RequestParam("itemStatus") ItemStatus itemStatus,
                             @RequestParam("itemDesc") String itemDesc){
        Item item=new Item();
        item.setId(itemId);
        item.setName(itemName);
        item.setPrice(itemPrice);
        item.setTag(itemTag);
        item.setStatus(itemStatus);
        item.setDescription(itemDesc);
        itemService.redisAddBook(item);
    }



}
