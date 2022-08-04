package com.busyfish.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.busyfish.server.exception.UserException.ErrorCode;
import com.busyfish.server.exception.UserException.UserException;

import com.busyfish.server.mapper.UserMapper;

import com.busyfish.server.model.User;
import com.busyfish.server.model.UserEnum.Shipping;
import com.busyfish.server.model.UserEnum.UserMemberShip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public List<User> selectAllUsers() {
        return userMapper.selectAllUsers();
    }

    public User selectUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }


    public List<User> queryUser(Integer id, String username, String primaryPhone, String email, UserMemberShip memberShip){
        List<User> userList=userMapper.queryUser(id,username,primaryPhone,email,memberShip==null?null: memberShip.name());
        return userList;
    }
    public void insertUser(User user) {
//        if (selectUserById(user.getId()) != null) {
//            throw new UserException(ErrorCode.DUPLICATE_USER, "User with this id already exists!");
//        }
        if (selectUserByUsername(user.getUsername()) != null) {
            throw new UserException(ErrorCode.DUPLICATE_USERNAME, "User with this username already exists!");
        }
        userMapper.insertUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getPrimaryPhone(), user.getMemberShip().toString());
    }

    @Transactional
    public void addShippingAddressToUser(Integer userId, Shipping shipping) {
        if (selectUserById(userId) == null) {
            throw new UserException(ErrorCode.NO_EXISTING_USER, "The user does not exist!");
        }
        HashMap<String, String> shippingInfo = new HashMap<>();
        shippingInfo.put("name", shipping.getName());
        shippingInfo.put("phone", shipping.getPhone());
        shippingInfo.put("address", shipping.getAddress());
        String shippingListKey = "user_" + userId + "_shippingList";
        String shippingKey = "user_" + userId + "_shipping_" + (int)Math.abs(shipping.hashCode()*Math.random()+Math.random()*1000);
        if (redisTemplate.boundListOps(shippingListKey) != null) {
            BoundListOperations list = redisTemplate.boundListOps(shippingListKey);
            if (list.size() >= 10L) {
                throw new UserException(ErrorCode.TOO_MANY_SHIPPING_ADDRESSES, "Cannot add more shipping address!");
            } else {
                list.rightPush(shippingKey);
            }
        } else {
            redisTemplate.boundListOps(shippingListKey).leftPushAll(shippingKey);
        }

        redisTemplate.opsForHash().putAll(shippingKey, shippingInfo);
    }

    @Transactional
    public void deleteShippingAddressFromUser(Integer userId, String shippingCode) {
        if (selectUserById(userId) == null) {
            throw new UserException(ErrorCode.NO_EXISTING_USER, "The user does not exist!");
        }
        String shippingListKey = "user_" + userId + "_shippingList";
        List<JSONObject> shippingAddressList = selectAllShippingAddressFromUser(userId);
        for (JSONObject shippingAddress : shippingAddressList) {
            if (shippingAddress.get("code").toString().equals(shippingCode)) {
                redisTemplate.delete(shippingCode);
                BoundListOperations list = redisTemplate.boundListOps(shippingListKey);
                list.remove(1L, shippingCode);
            }
        }
    }

    @Transactional
    public void updateShippingAddress(String shippingCode, Shipping shipping) {
        if(redisTemplate.hasKey(shippingCode)==false){
            throw new UserException(ErrorCode.NO_EXISTING_SHIPPING_ADDRESS,"This shipping address does not exist!");
        }
        if(shipping.getName()!=null){
            redisTemplate.boundHashOps(shippingCode).put("name",shipping.getName());
        }

        if(shipping.getAddress()!=null){
            redisTemplate.boundHashOps(shippingCode).put("address",shipping.getAddress());
        }

        if(shipping.getPhone()!=null){
            redisTemplate.boundHashOps(shippingCode).put("phone",shipping.getPhone());
        }

    }

    @Transactional
    public List<JSONObject> selectAllShippingAddressFromUser(Integer userId) {
        String shippingListKey = "user_" + userId + "_shippingList";
        List<String> shippingCodeList = redisTemplate.boundListOps(shippingListKey).range(0L, -1L);
        List<JSONObject> shippingAddressList = new ArrayList<>();
        for (String shippingCode : shippingCodeList) {
            Map infoMap = redisTemplate.boundHashOps(shippingCode).entries();
            infoMap.put("code", shippingCode);
            JSONObject infoJSONObject = JSONObject.parseObject(JSON.toJSONString(infoMap));
            shippingAddressList.add(infoJSONObject);
        }

        return shippingAddressList;
    }

    public String login(String username, String password) {
        User user = selectUserByUsername(username);
        if (user == null) {
            throw new UserException(ErrorCode.NO_EXISTING_USERNAME, "User with this username does not exist, please check your username!");
        } else if (!user.getPassword().equals(password)) {
            throw new UserException(ErrorCode.WRONG_PASSWORD, "Password is not correct, please try again!");
        } else if (user.getMemberShip().equals(UserMemberShip.FROZEN) || user.getMemberShip().equals(UserMemberShip.BANNED)) {
            throw new UserException(ErrorCode.LOCKED_ACCOUNT, "The account is locked due to exceptions, please contact the administrators");
        }

        return "Login Successfully";

    }

    @Transactional
    public void updateUser(User userUpdate){
        User userCurrent=selectUserById(userUpdate.getId());
        if(userCurrent==null){
            throw new UserException(ErrorCode.NO_EXISTING_USER,"Cannot find the user, so fail to update!");
        }

        if(!userUpdate.getUsername().equals(userCurrent.getUsername())){
            if (selectUserByUsername(userUpdate.getUsername()) != null) {
                throw new UserException(ErrorCode.DUPLICATE_USERNAME, "User with this username already exists!");
            }
        }

        try{
            userMapper.updateUser(userUpdate.getId(), userUpdate.getUsername(), userUpdate.getPassword(), userUpdate.getEmail(), userUpdate.getPrimaryPhone(), userUpdate.getMemberShip().toString());
        }catch (Exception e){
            throw new UserException(ErrorCode.USER_UPDATE_FAILURE,"Fail to update user!");
        }
    }

    @Transactional
    public String setSessionId(HttpServletRequest request, User user){
        request.getSession().setAttribute("userId",user.getId());
        request.getSession().setAttribute("username",user.getUsername());
        return "Session setting successfully. <br/> userId: "+user.getId();

    }

    @Transactional
    public String getSessionId(HttpServletRequest request){
        int port=request.getServerPort();
        String sessionId=request.getSession().getId();
        String userId=request.getSession().getAttribute("userId").toString();

        return "Port: "+ port
                + "<br/>sessionId: "+sessionId
                +"<br/>userId: "+userId;
    }

    @Transactional
    public void updateUserProfile(User userUpdate){

        User userCurrent=selectUserById(userUpdate.getId());

        if(userCurrent==null){
            throw new UserException(ErrorCode.NO_EXISTING_USER,"Cannot find the user, so fail to update!");
        }

        if(!userUpdate.getUsername().equals(userCurrent.getUsername())){
            if (selectUserByUsername(userUpdate.getUsername()) != null) {
                throw new UserException(ErrorCode.DUPLICATE_USERNAME, "User with this username already exists!");
            }
        }

        try{
            userMapper.updateUserProfile(userUpdate.getId(), userUpdate.getUsername(), userUpdate.getEmail(), userUpdate.getPrimaryPhone());
        }catch (Exception e){
            throw new UserException(ErrorCode.USER_UPDATE_FAILURE,"Fail to update user profile!");
        }

    }

    @Transactional
    public void updatePassword(Integer id, String newPassword, String oldPassword){
        User user=selectUserById(id);
        if(user==null){
            throw new UserException(ErrorCode.NO_EXISTING_USER,"User not found!");
        }
        if(user.getPassword().equals(oldPassword)){
            userMapper.updatePassword(id,newPassword);
        }else{
            throw new UserException(ErrorCode.WRONG_PASSWORD,"Password is not correct, fail to modify password!");
        }
    }

}
