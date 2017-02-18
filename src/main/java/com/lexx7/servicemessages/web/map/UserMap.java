package com.lexx7.servicemessages.web.map;

import com.lexx7.servicemessages.model.entity.User;
import com.lexx7.servicemessages.web.dto.ReplacePasswordUserForm;
import com.lexx7.servicemessages.web.dto.UserForm;
import com.lexx7.servicemessages.web.dto.UserRoleForm;


public class UserMap {

    public static User mapUserFormToUser(UserForm userForm) {

        User user = new User();
        user.setId(userForm.getId() == null || userForm.getId().isEmpty() ? null : Long.valueOf(userForm.getId()));
        user.setLogin(userForm.getLogin());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setMiddleName(userForm.getMiddleName());
        user.setEmail(userForm.getEmail());
        user.setLogin(userForm.getLogin());
        user.setPassword(userForm.getPassword());

        return user;
    }

    public static ReplacePasswordUserForm mapToUserReplacePasswordUserForm(User user) {

        ReplacePasswordUserForm replaceForm = new ReplacePasswordUserForm();
        replaceForm.setId(user.getId().toString());
        replaceForm.setPassword("");
        return replaceForm;
    }

    public static UserRoleForm mapUserToUserRoleForm(User user) {

        UserRoleForm userRoleForm = new UserRoleForm();
        userRoleForm.setId(user.getId().toString());
        userRoleForm.setAdmin(user.isAdmin());

        return userRoleForm;
    }
}
