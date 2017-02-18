package com.lexx7.servicemessages.web.dto;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Time;

public class MessageForm {

    private String id;

    @Pattern(regexp = "^[0-9]+?", message = "Допустимы только цифры")
    private String userTo;

    @Size(min=3, message = "ФИО получателя не может быть короче 3 символов")
    private String userToText;

    @Size(min = 2, max=255, message = "Тема должна содержать от 2 до 255 символов")
    private String theme;

    @NotNull(message = "Сообщение не должно быть пустым")
    @Size(max = 2000, message = "Сообщение не должно быть длиннее 2000 символов")
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserToText() {
        return userToText;
    }

    public void setUserToText(String userToText) {
        this.userToText = userToText;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageForm{" +
                "id='" + id + '\'' +
                ", userTo='" + userTo + '\'' +
                ", userToText='" + userToText + '\'' +
                ", theme='" + theme + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
