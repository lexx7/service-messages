package com.lexx7.servicemessages.web.validator;

import com.lexx7.servicemessages.business.service.UserService;
import com.lexx7.servicemessages.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqUsernameConstraintValidator implements ConstraintValidator<UniqUsername, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        try {
            userService.getUserByLogin(value);
        } catch (NoResultException e){
            return true;
        }

        return false;
    }
}
