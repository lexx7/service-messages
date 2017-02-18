package com.lexx7.servicemessages.web.dto;

import com.lexx7.servicemessages.web.validator.UniqUsername;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class UserForm {

    private String id;

    @NotNull(message = "Имя должно быть задано")
    @Size(min = 2, max = 255, message = "Имя недопустимого размера")
    private String firstName;

    @NotNull(message = "Фамилия должна быть задана")
    @Size(min = 2, max = 255, message = "Фамилия недопустимого размера")
    private String lastName;

    @Size(max = 255, message = "Отчество недопустимого размера")
    private String middleName;

    @NotNull(message = "Email должен быть заполнен")
    @Pattern(regexp = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
            "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
            "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
            "+(?:[a-zA-Z]){2,}\\.?)$",
            message = "Заданный email не может существовать")
    private String email;

    @UniqUsername
    @NotNull(message = "Логин должен быть заполнен")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Недопустимый логин")
    @Size(min = 3, max = 255, message = "Логин должен содержать от 3 до 255 символов")
    private String login;

    @NotNull(message = "Пароль должен быть заполнен")
    @Size(min = 8, max = 100, message = "Пароль должен быть длиннее 8 символов")
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

