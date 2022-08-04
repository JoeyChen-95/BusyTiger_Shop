package com.busyfish.server.mapper;

import com.busyfish.server.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectAllUsers();

    User selectUserById(Integer id);

    User selectUserByUsername(String username);

    void insertUser(String username, String password, String email, String primaryPhone, String memberShip);

    void updateUser(Integer id, String username,String password, String email, String primaryPhone, String memberShip);
    void updateUserProfile(Integer id, String username,String email, String primaryPhone);

    List<User> queryUser(Integer id, String username, String primaryPhone, String email, String memberShip);

    void updatePassword(Integer id, String newPassword);
}
