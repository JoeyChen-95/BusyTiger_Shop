package com.example.server.service;

import com.example.server.mapper.ItemMapper;
import com.example.server.mapper.UserMapper;
import com.example.server.model.Item;
import com.example.server.model.User;
import com.example.server.model.UserEnum.Shipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
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

    public User insertUser(User user){
        userMapper.insertUser(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPrimaryPhone(),user.getMemberShip().toString());
        return user;
    }

    public Shipping addShippingAddressToUser(Integer userId, Shipping shipping){
        HashMap<String,String> shippingInfo=new HashMap<>();
        shippingInfo.put("name",shipping.getName());
        shippingInfo.put("phone",shipping.getPhone());
        shippingInfo.put("address",shipping.getAddress());
        redisTemplate.opsForHash().putAll("user_{}_shipping_{}".format(userId.toString(),1),shippingInfo);
        return shipping;
    }

}
