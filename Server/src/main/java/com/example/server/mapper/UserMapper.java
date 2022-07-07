package com.example.server.mapper;

import com.example.server.model.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<Item> selectAllUsers();
    Item selectUserById(Integer id);
    void insertUser(Integer id);
}
