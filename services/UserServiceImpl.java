package com.empresa.crudjpa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.crudjpa.entities.Role;
import com.empresa.crudjpa.entities.User;
import com.empresa.crudjpa.repositories.IRoleRepository;
import com.empresa.crudjpa.repositories.IUserRepository;
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository repositoryUser;
    @Autowired
    private IRoleRepository repositoryRole;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return  (List<User>) repositoryUser.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        Optional<Role> optionalRoleUser = repositoryRole.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        optionalRoleUser.ifPresent(role -> roles.add(role));
        if (user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = repositoryRole.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(role -> roles.add(role));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repositoryUser.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repositoryUser.existsByUsername(username);
    }

}
