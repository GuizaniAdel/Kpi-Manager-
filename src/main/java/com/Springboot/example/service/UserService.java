package com.Springboot.example.service;

import com.Springboot.example.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    User findByEmail(String email);
}
