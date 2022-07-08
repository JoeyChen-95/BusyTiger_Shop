package com.example.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.server.mapper.ItemMapper;
import com.example.server.mapper.UserMapper;
import com.example.server.model.Item;
import com.example.server.model.User;
import com.example.server.model.UserEnum.Shipping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<User> selectAllUsers(){
        return userMapper.selectAllUsers();
    }

    public User selectUserById(Integer id){
        return userMapper.selectUserById(id);
    }
    public User selectUserByUsername(String username){
        return userMapper.selectUserByUsername(username);
    }

    public User insertUser(User user){
        userMapper.insertUser(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPrimaryPhone(),user.getMemberShip().toString());
        return user;
    }
    @Transactional
    public Shipping addShippingAddressToUser(Integer userId, Shipping shipping){
        HashMap<String,String> shippingInfo=new HashMap<>();
        shippingInfo.put("name",shipping.getName());
        shippingInfo.put("phone",shipping.getPhone());
        shippingInfo.put("address",shipping.getAddress());
        String shippingListKey="user_"+userId+"_shippingList";
        String shippingKey= "user_"+userId+"_shipping_"+Math.abs(shipping.hashCode());
        if(redisTemplate.boundListOps(shippingListKey)!=null){
            BoundListOperations list = redisTemplate.boundListOps(shippingListKey);
            list.rightPush(shippingKey);
        }else{
            redisTemplate.boundListOps(shippingListKey).leftPushAll(shippingKey);
        }

        redisTemplate.opsForHash().putAll(shippingKey,shippingInfo);
        return shipping;
    }

    @Transactional
    public JSONObject deleteShippingAddressFromUser(Integer userId, Integer shippingCode){
        String shippingListKey="user_"+userId+"_shippingList";
        String code ="user_"+userId+"_shipping_"+shippingCode;
        List<JSONObject> shippingAddressList=selectAllShippingAddressFromUser(userId);
        for(JSONObject shippingAddress:shippingAddressList){
            System.out.println(shippingAddress.get("code").toString());
            System.out.println(code);
            if(shippingAddress.get("code").toString().equals(code)){
                redisTemplate.delete(code);
                BoundListOperations list = redisTemplate.boundListOps(shippingListKey);
                list.remove(1L,code);
                return shippingAddress;
            }
        }
        return null;
    }

    @Transactional
    public List<JSONObject> selectAllShippingAddressFromUser(Integer userId){
        String shippingListKey="user_"+userId+"_shippingList";
        List<String> shippingCodeList=redisTemplate.boundListOps(shippingListKey).range(0L,-1L);
        List<JSONObject> shippingAddressList=new ArrayList<>();
        for(String shippingCode:shippingCodeList){
            Map infoMap=redisTemplate.boundHashOps(shippingCode).entries();
            infoMap.put("code",shippingCode);
            JSONObject infoJSONObject=JSONObject.parseObject(JSON.toJSONString(infoMap));
            shippingAddressList.add(infoJSONObject);
        }

        return shippingAddressList;
    }

    public String login(String username, String password){
        User user=selectUserByUsername(username);
        if(user!=null&&user.getPassword().equals(password)){
            return "yes";
        }else{
            return "-1";
        }
    }

}
