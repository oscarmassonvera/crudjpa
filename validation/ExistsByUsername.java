package com.empresa.crudjpa.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExisByUsernameValidation.class)
public @interface ExistsByUsername {
   String message() default "YA EXISTE EN LA BBDD....";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};

}
