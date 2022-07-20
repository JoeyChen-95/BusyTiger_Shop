package com.busyfish.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.busyfish.server.exception.UserException.UserException;

import com.busyfish.server.model.User;
import com.busyfish.server.model.UserEnum.Shipping;
import com.busyfish.server.model.UserEnum.UserMemberShip;

import com.busyfish.server.service.UserService;
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
    public List<User> selectAllUsers() {
        return userService.selectAllUsers();
    }

    @GetMapping(value = "/selectUserById")
    public User selectUserById(@RequestParam("userId") Integer userId) {
        return userService.selectUserById(userId);
    }

    @GetMapping(value = "/selectUserByUsername")
    public User selectUserByUsername(@RequestParam("username") String username) {
        return userService.selectUserByUsername(username);
    }

    @PostMapping(value = "/insertUser")
    public ResponseBody insertUser(@RequestParam("username") String username,
                                   @RequestParam("userPassword") String userPassword,
                                   @RequestParam("userEmail") String userEmail,
                                   @RequestParam("userPrimaryPhone") String userPrimaryPhone,
                                   @RequestParam("userMemberShip") UserMemberShip userMemberShip) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(userPassword);
        user.setEmail(userEmail);
        user.setPassword(userPassword);
        user.setPrimaryPhone(userPrimaryPhone);
        user.setMemberShip(userMemberShip);
        try {
            userService.insertUser(user);
        } catch (UserException e) {
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, "Add user successfully");
    }

    @PostMapping(value = "/addShippingAddressToUser")
    public ResponseBody addShippingAddressToUser(@RequestParam("userId") Integer userId,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("phone") String phone,
                                                 @RequestParam("address") String address) {
        Shipping shipping = new Shipping();
        shipping.setName(name);
        shipping.setPhone(phone);
        shipping.setAddress(address);
        try {
            userService.addShippingAddressToUser(userId, shipping);
        } catch (UserException e) {
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, "Add shipping address successfully");
    }

    @DeleteMapping(value = "/deleteShippingAddressFromUser")
    public ResponseBody deleteShippingAddressFromUser(@RequestParam("userId") Integer userId,
                                                      @RequestParam("shippingNum") Integer shippingNum) {
        try {
            userService.deleteShippingAddressFromUser(userId, shippingNum);
        } catch (UserException e) {
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, "Delete shipping address successfully");
    }

    @GetMapping(value = "/selectAllShippingAddressFromUser")
    public List<JSONObject> selectAllShippingAddressFromUser(@RequestParam("userId") Integer userId) {
        return userService.selectAllShippingAddressFromUser(userId);
    }

    @GetMapping(value = "/login")
    public ResponseBody login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        try {
            userService.login(username, password);
        } catch (UserException e) {
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, "Login successfully");
    }


}
