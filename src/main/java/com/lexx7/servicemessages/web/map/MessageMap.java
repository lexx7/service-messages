package com.lexx7.servicemessages.web.map;

import com.lexx7.servicemessages.model.entity.Message;
import com.lexx7.servicemessages.model.entity.User;
import com.lexx7.servicemessages.web.dto.MessageForm;

public class MessageMap {

    public static Message mapMessageFormToMessage(MessageForm messageForm) {

        Message message = new Message();
        message.setId(messageForm.getId() == null || messageForm.getId().isEmpty() ? null :
                Long.valueOf(messageForm.getId()));
        message.setToUser(new User(Long.valueOf(messageForm.getUserTo())));
        message.setTheme(messageForm.getTheme());
        message.setMessage(messageForm.getMessage());

        return message;
    }

    public static MessageForm mapMessageToMessageForm(Message message) {

        User userTo = message.getToUser();
        String userName = userTo.getFullName();

        MessageForm messageForm = new MessageForm();
        messageForm.setId(message.getId().toString());
        messageForm.setUserTo(userName);
        messageForm.setTheme(message.getTheme());
        messageForm.setMessage(message.getMessage());

        return messageForm;
    }
}
