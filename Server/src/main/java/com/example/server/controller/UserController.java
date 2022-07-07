package com.example.server.controller;

import com.example.server.model.Item;
import com.example.server.model.ItemEnum.ItemStatus;
import com.example.server.model.ItemEnum.Tag;
import com.example.server.model.User;
import com.example.server.model.UserEnum.UserMemberShip;
import com.example.server.service.ItemService;
import com.example.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/selectAllUsers")
    public List<User> selectAllUsers(){
        return userService.selectAllUsers();
    }

    @GetMapping(value = "/selectUserById")
    public User selectUserById(@RequestParam("userId") Integer userId){
        return userService.selectUserById(userId);
    }

    @PostMapping(value = "/insertUser")
    public User insertUser(@RequestParam("userId") Integer userId,
                           @RequestParam("username") String username,
                           @RequestParam("userPassword") String userPassword,
                           @RequestParam("userEmail") String userEmail,
                           @RequestParam("userPrimaryPhone") String userPrimaryPhone,
                           @RequestParam("userMemberShip") UserMemberShip userMemberShip){
        User user=new User();
        user.setId(userId);
        user.setUsername(username);
        user.setPassword(userPassword);
        user.setEmail(userEmail);
        user.setPassword(userPassword);
        user.setPrimaryPhone(userPrimaryPhone);
        user.setMemberShip(userMemberShip);
        return userService.insertUser(user);
    }
//    @PostMapping(value = "/redisAddItem")
//    public void redisAddBook(@RequestParam("itemId") Integer itemId,
//                             @RequestParam("itemName") String itemName,
//                             @RequestParam("itemPrice") Integer itemPrice,
//                             @RequestParam("itemTag") Tag itemTag,
//                             @RequestParam("itemStatus") ItemStatus itemStatus,
//                             @RequestParam("itemDesc") String itemDesc){
//        Item item=new Item();
//        item.setId(itemId);
//        item.setName(itemName);
//        item.setPrice(itemPrice);
//        item.setTag(itemTag);
//        item.setStatus(itemStatus);
//        item.setDescription(itemDesc);
//        itemService.redisAddBook(item);
//    }



}
