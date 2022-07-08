package com.example.server.mapper;

import com.example.server.model.Item;
import com.example.server.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectAllUsers();
    User selectUserById(Integer id);

    User selectUserByUsername(String username);
    void insertUser(Integer id, String username, String password, String email,String primaryPhone,String memberShip);
}
