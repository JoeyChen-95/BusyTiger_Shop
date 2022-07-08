package com.example.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.server.model.Item;
import com.example.server.model.ItemEnum.ItemStatus;
import com.example.server.model.ItemEnum.Tag;
import com.example.server.model.User;
import com.example.server.model.UserEnum.Shipping;
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

    @GetMapping(value = "/selectUserByUsername")
    public User selectUserByUsername(@RequestParam("username") String username){
        return userService.selectUserByUsername(username);
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
    @PostMapping(value = "/addShippingAddressToUser")
    public Shipping addShippingAddressToUser(@RequestParam("userId") Integer userId,
                             @RequestParam("name") String name,
                             @RequestParam("phone") String phone,
                             @RequestParam("address") String address){
        Shipping shipping=new Shipping();
        shipping.setName(name);
        shipping.setPhone(phone);
        shipping.setAddress(address);
        return userService.addShippingAddressToUser(userId,shipping);
    }

    @DeleteMapping(value = "/deleteShippingAddressFromUser")
    public JSONObject deleteShippingAddressFromUser(@RequestParam("userId") Integer userId,
                                                  @RequestParam("shippingNum") Integer shippingNum){
        return userService.deleteShippingAddressFromUser(userId, shippingNum);
    }

    @GetMapping(value = "/selectAllShippingAddressFromUser")
    public List<JSONObject> selectAllShippingAddressFromUser(@RequestParam("userId") Integer userId){
        return userService.selectAllShippingAddressFromUser(userId);
    }

    @GetMapping(value = "/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        return userService.login(username,password);
    }



}
