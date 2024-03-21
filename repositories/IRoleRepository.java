package com.empresa.crudjpa.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.empresa.crudjpa.entities.Role;

public interface IRoleRepository extends CrudRepository<Role,Long> {

    
    Optional<Role> findByName(String name);

}
