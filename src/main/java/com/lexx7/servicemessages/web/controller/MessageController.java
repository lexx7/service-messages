package com.lexx7.servicemessages.web.controller;


import com.lexx7.servicemessages.web.map.MessageMap;
import com.lexx7.servicemessages.business.service.AddressBookService;
import com.lexx7.servicemessages.business.service.MessageService;
import com.lexx7.servicemessages.business.service.UserService;
import com.lexx7.servicemessages.model.entity.AddressBook;
import com.lexx7.servicemessages.model.entity.Message;
import com.lexx7.servicemessages.model.entity.User;
import com.lexx7.servicemessages.web.dto.MessageForm;

import com.lexx7.servicemessages.web.model.ListDataModel;
import com.lexx7.servicemessages.web.model.MessageRowModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("message")
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @RequestMapping(value = "send/{addressId}", method = RequestMethod.GET)
    public String showSendMessageForm(@PathVariable Long addressId, Model model) {
        LOGGER.debug("Show send messageForm addressId=" + addressId.toString());

        //FIXME: no valid on recipient of the current authorized user
        AddressBook address = addressBookService.getAddress(addressId);
        User toUser = address.getToUser();

        MessageForm messageForm = new MessageForm();
        messageForm.setUserTo(toUser.getId().toString());
        messageForm.setUserToText(toUser.getFullName());

        model.addAttribute("messageForm", messageForm);
        return "message-send";
    }

    @RequestMapping(value = "send/{addressId}", method = RequestMethod.POST)
    public String submitSendMessageForm(@PathVariable Long addressId, @Valid MessageForm messageForm, BindingResult result) {
        LOGGER.debug("submit SendMessageForm: " + messageForm.toString());

        if (!result.hasErrors()) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Select only current authentication user
            User user = new User();
            if (authentication != null && authentication.isAuthenticated() && authentication.getName() != "anonymousUser") {
                user = userService.getUserByLogin(authentication.getName());
            }

            Message message = MessageMap.mapMessageFormToMessage(messageForm);
            message.setFromUser(user);
            messageService.createMessage(message);

            return "redirect:/address-book";
        } else {
            LOGGER.debug("submit SendMessageForm error: " + result.toString());
            return "message-send";
        }
    }

    @RequestMapping(value = "{messageId}", method = RequestMethod.GET)
    public String viewMessageForm(@PathVariable Long messageId, Model model) {
        LOGGER.debug("Show view messageForm");

        Message message = messageService.getMessage(messageId);
        MessageForm messageForm = MessageMap.mapMessageToMessageForm(message);

        model.addAttribute("messageForm", messageForm);
        model.addAttribute("createDate", message.getCreateTime().toString());
        return "message";
    }

    @RequestMapping(value = "/remove/{messageId}", method = RequestMethod.GET)
    public String removeMessage(@PathVariable String messageId) {
        LOGGER.debug("Remove message: id=" + Long.valueOf(messageId));

        messageService.removeAddress(Long.valueOf(messageId));

        return "empty";
    }

    // List for grid
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public @ResponseBody
    ListDataModel<MessageRowModel> listMessages(
            Integer page, Integer rows, String sidx, String sord) {
        LOGGER.debug("list Messages");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Select only current authentication user
        User user = new User();
        if (authentication != null && authentication.isAuthenticated() && authentication.getName() != "anonymousUser") {
            user = userService.getUserByLogin(authentication.getName());
        }

        // Rows elements message for grid
        ArrayList<MessageRowModel> list = new ArrayList<>();
        List<Message> result = messageService.getList(page, rows, sidx, sord, user);

        for (Message row : result) {
            MessageRowModel model = new MessageRowModel();
            model.setId(row.getId().toString());
            model.setCreateDate(row.getCreateTime().toString());
            model.setFromUser(row.getFromUser().getFullName());
            model.setTheme(row.getTheme());
            model.setToUser(row.getToUser().getFullName());
            list.add(model);
        }

        Long count = messageService.getCountMessages(user);

        // Calculate totalPages for grid
        Long totalPages = Long.valueOf("0");
        if (Long.compare(count, 0) > 0) {
            Double total = (Double) Math.ceil( (double) count / rows );
            totalPages = total.longValue();
        }

        ListDataModel<MessageRowModel> data = new ListDataModel<>();
        data.setRows(list);
        data.setPage(Long.valueOf(page));
        data.setTotal(totalPages);
        data.setRecords(count);

        LOGGER.debug("ListDataModel: " + data.toString());

        return data;
    }
}
