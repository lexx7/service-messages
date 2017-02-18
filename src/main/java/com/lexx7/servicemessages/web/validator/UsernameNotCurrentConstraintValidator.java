package com.lexx7.servicemessages.web.validator;

import com.lexx7.servicemessages.business.service.AddressBookService;
import com.lexx7.servicemessages.business.service.UserService;
import com.lexx7.servicemessages.model.entity.AddressBook;
import com.lexx7.servicemessages.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.NoResultException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UsernameNotCurrentConstraintValidator implements ConstraintValidator<UsernameNotCurrent, String> {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Override
    public void initialize(UsernameNotCurrent constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Select only current authentication user
        User fromUser = new User();
        if (authentication != null && authentication.isAuthenticated() && authentication.getName() != "anonymousUser") {
            fromUser = userService.getUserByLogin(authentication.getName());
        }

        User toUser = new User(Long.valueOf(value));

        // Not current user
        if (toUser.getId().compareTo(fromUser.getId()) == 0) {
            return false;
        }

        try {
            addressBookService.getAddressByToUserAndFromUser(toUser, fromUser);
        } catch (NoResultException e){
            return true;
        }

        return false;
    }
}
