package com.empresa.crudjpa.services;

import java.util.List;

import com.empresa.crudjpa.entities.User;

public interface IUserService {
    List<User> findAll();
    User save(User user);
    boolean existsByUsername(String username);
}
