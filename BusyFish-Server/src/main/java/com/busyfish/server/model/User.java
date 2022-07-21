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

    public User() {
    }

    public User(Integer id, String username, String password, String email, String primaryPhone, UserMemberShip memberShip) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.primaryPhone = primaryPhone;
        this.memberShip = memberShip;
    }
}
