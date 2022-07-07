package com.example.server.model;
import com.example.server.model.UserEnum.ShippingManagement;
import com.example.server.model.UserEnum.UserMemberShip;
import lombok.Data;

@Data
public class User {
    Integer id;
    String username;
    String password;
    String email;
    String primaryPhone;
    ShippingManagement shippingManagement;
    UserMemberShip memberShip;
}
