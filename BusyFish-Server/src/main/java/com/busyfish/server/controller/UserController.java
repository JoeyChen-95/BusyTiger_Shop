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

import javax.servlet.http.HttpServletRequest;
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
    public ResponseBody login(HttpServletRequest request,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password) {
//        try{
//            int port= request.getServerPort();
//            String sessionId=request.getSession().getId();
//            System.out.println(request.getSession().getAttribute("userId"));
//            System.out.println("Try Session ID"+request.getSession().getId());
//            String userId=request.getSession().getAttribute("userId").toString();
//            return new ResponseBody(200,userId+" Login successfully with session");
//        }catch (Exception e){
//
//        }

        try {
            userService.login(username, password);
            request.getSession().setAttribute("userId",selectUserByUsername(username).getId().toString());
            request.getSession().setAttribute("username",username);
            log.info("Login: Set Session ID "+request.getSession().getId());
            log.info("Login: IP Address sending request: "+request.getRemoteAddr()+":"+request.getRemotePort());

        } catch (UserException e) {
            return new ResponseBody(500, e.getMessage());
        }

        return new ResponseBody(200, "Login successfully (First Time)");
    }

    @GetMapping(value = "/getCurrentUserId")
    public User getCurrentSessionUserId(HttpServletRequest request){
        if(request.getSession().getAttribute("userId")==null){

            log.info("getCurrentSessionUserId: User Id is null!");
            log.info("getCurrentSessionUserId: Current Session ID-->"+request.getSession().getId());
            log.info("getCurrentSessionUserId: IP Address sending request: "+request.getRemoteAddr()+":"+request.getRemotePort());

            return null;
        }else{
            Integer userId= Integer.valueOf(request.getSession().getAttribute("userId").toString());
            String username= request.getSession().getAttribute("username").toString();

            log.info("getCurrentSessionUserId: User Id found!");
            log.info("getCurrentSessionUserId: Current Session Id-->"+userId+"  Current Session Id-->"+request.getSession().getId());
            log.info("getCurrentSessionUserId: IP Address sending request: "+request.getRemoteAddr()+":"+request.getRemotePort());

            User user=selectUserById(userId);
            user.setPassword("????????");
            return user;
        }
    }

    @GetMapping(value = "/deleteSession")
    public void deleteSession(HttpServletRequest request){
        request.getSession().invalidate();
    }

    @PutMapping(value = "/updateUser")
    public ResponseBody updateUser(@RequestParam("id") Integer id,
                                   @RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("email") String email,
                                   @RequestParam("primaryPhone") String primaryPhone,
                                   @RequestParam("memberShip") UserMemberShip memberShip){
        try{
            User user=new User(id,username,password,email,primaryPhone,memberShip);
            userService.updateUser(user);
        }catch (Exception e){
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, "Update user successfully");
    }

    @PutMapping(value = "/updateUserProfile")
    public ResponseBody updateUserProfile(@RequestParam("id") Integer id,
                                   @RequestParam("username") String username,
                                   @RequestParam("email") String email,
                                   @RequestParam("primaryPhone") String primaryPhone){
        try{
            User user=new User(id,username,"",email,primaryPhone,null);
            userService.updateUserProfile(user);
        }catch (Exception e){
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, "Update user profile successfully");
    }




}
