package com.busyfish.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.busyfish.server.config.UserDataConfig;
import com.busyfish.server.exception.UserException.UserException;

import com.busyfish.server.model.Img;
import com.busyfish.server.model.User;
import com.busyfish.server.model.UserEnum.Shipping;
import com.busyfish.server.model.UserEnum.UserMemberShip;

import com.busyfish.server.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


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

    @PostMapping(value = "/queryUser")
    public List<User> queryUser(Integer id, String username, String primaryPhone, String email, UserMemberShip memberShip){
        return userService.queryUser(id, username, primaryPhone, email, memberShip);
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
                                                      @RequestParam("shippingCode") String shippingCode) {
        try {
            userService.deleteShippingAddressFromUser(userId, shippingCode);
        } catch (UserException e) {
            return new ResponseBody(500, e.getMessage());
        }
        return new ResponseBody(200, "Delete shipping address successfully");
    }

    @GetMapping(value = "/selectAllShippingAddressFromUser")
    public List<JSONObject> selectAllShippingAddressFromUser(@RequestParam("userId") Integer userId) {
        return userService.selectAllShippingAddressFromUser(userId);
    }

    @PutMapping(value = "/updateShippingAddress")
    public ResponseBody updateShippingAddress(@RequestParam("shippingCode") String shippingCode,
                                              String name,
                                              String phone,
                                              String address){
        Shipping shipping=new Shipping();
        shipping.setPhone(phone);
        shipping.setName(name);
        shipping.setAddress(address);
        try{
            userService.updateShippingAddress(shippingCode,shipping);
            return new ResponseBody(200,"Update shipping address successfully");
        }catch (Exception e){
            return new ResponseBody(500,e.getMessage());
        }
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

    @PutMapping(value = "/updateUserPassword")
    public ResponseBody updateUserPassword(@RequestParam("id") Integer id,
                                           @RequestParam("newPassword") String newPassword,
                                           @RequestParam("oldPassword") String oldPassword){
        try{
            userService.updatePassword(id, newPassword, oldPassword);
            return new ResponseBody(200,"Modify password successfully");
        }catch (Exception e){
            return new ResponseBody(500,e.getMessage());
        }
    }

    @RequestMapping(value = "/addImg")
    public String uploadUserProfileImg(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "userId") Integer userId) throws IOException {
        /**
         * 上传图片
         */
        System.out.println("Set user's profile image for: user-"+userId);
        //图片上传成功后，将图片的地址写到数据库
        //保存图片的路径（这是存在我项目中的images下了，你们可以设置路径）
        String filePath = UserDataConfig.LOCAL_USER_PROFILE_IMG_DIR;
        //获取原始图片的拓展名
        String originalFilename = file.getOriginalFilename();
        //新的文件名字
        //Local File Name:" user_profile_img_{userId}.jpg/jpeg/png/...
        String newFileName = "user_profile_img_"+userId +".jpg";
        //封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);
        //把本地文件上传到封装上传文件位置的全路径
        file.transferTo(targetFile);

        return "User "+userId+" 's profile image is set successfully!";
    }
}
