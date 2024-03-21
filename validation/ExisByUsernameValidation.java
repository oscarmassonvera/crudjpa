package com.empresa.crudjpa.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.empresa.crudjpa.services.IUserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExisByUsernameValidation implements ConstraintValidator<ExistsByUsername,String>{
    @Autowired
    private IUserService serviceUser;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if( serviceUser == null ) {return true;}
        return !serviceUser.existsByUsername(username);
    }

    

}
