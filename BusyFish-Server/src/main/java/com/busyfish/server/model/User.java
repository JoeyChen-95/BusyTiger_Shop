package com.busyfish.server.model;

import com.busyfish.server.model.UserEnum.Shipping;
import com.busyfish.server.model.UserEnum.UserMemberShip;
import lombok.Data;

import java.util.List;

@Data
public class User {
    Integer id;
    String username;
    String password;
    String email;
    String primaryPhone;
    List<Shipping> shippingList;
    UserMemberShip memberShip;
}
