package com.lexx7.servicemessages.web.dto;

import com.lexx7.servicemessages.web.validator.UniqUsername;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class UserRoleForm {

    @NotNull
    private String id;

    private boolean admin = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "id='" + id + '\'' +
                ", admin=" + admin +
                '}';
    }
}

