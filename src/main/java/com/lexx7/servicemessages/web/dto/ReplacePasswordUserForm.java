package com.lexx7.servicemessages.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class ReplacePasswordUserForm {

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String id;

    @NotNull(message = "Пароль должен быть заполнен")
    @Size(min = 8, max = 100, message = "Пароль должен быть длиннее 8 символов")
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

