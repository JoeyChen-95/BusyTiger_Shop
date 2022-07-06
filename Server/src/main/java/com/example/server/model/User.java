package com.example.server.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
    Integer id;
    String username;
    String password;
    List<Integer> favoriteBooks;

}
