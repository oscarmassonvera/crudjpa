package com.empresa.crudjpa.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.empresa.crudjpa.entities.User;

public interface IUserRepository extends CrudRepository<User,Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
