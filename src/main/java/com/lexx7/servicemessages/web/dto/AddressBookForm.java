package com.lexx7.servicemessages.web.dto;


import com.lexx7.servicemessages.web.validator.UsernameNotCurrent;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddressBookForm {

    @UsernameNotCurrent
    @NotNull(message = "Обязательно для заполнения")
    @Pattern(regexp = "^[0-9]+?", message = "Допустимы только цифры")
    private String userTo;

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    @Override
    public String toString() {
        return "AddressBookForm{" +
                "userTo=" + userTo +
                '}';
    }
}
