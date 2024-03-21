package com.empresa.crudjpa.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.crudjpa.entities.User;
import com.empresa.crudjpa.services.IUserService;


import jakarta.validation.Valid;

// spring.jpa.hibernate.ddl-auto=create

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public List<User> list(){
        return userService.findAll();
    }

    // PARA REGISTRAR ADMINS
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        user.setAdmin(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    // PARA REGISTRAR USUARIOS NOMALUCHOS
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result){
        user.setAdmin(false);
        return create(user,result);
    }    

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errors.put(err.getField(),"El campo "+ err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
