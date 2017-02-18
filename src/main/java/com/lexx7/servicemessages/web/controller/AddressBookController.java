package com.lexx7.servicemessages.web.controller;


import com.lexx7.servicemessages.business.service.AddressBookService;
import com.lexx7.servicemessages.business.service.UserService;
import com.lexx7.servicemessages.model.entity.AddressBook;
import com.lexx7.servicemessages.model.entity.User;
import com.lexx7.servicemessages.web.dto.AddressBookForm;
import com.lexx7.servicemessages.web.dto.AddressBookFormData;
import com.lexx7.servicemessages.web.model.AddressBookRowModel;
import com.lexx7.servicemessages.web.model.ListDataModel;
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

import javax.persistence.NoResultException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequestMapping("address-book")
public class AddressBookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressBookController.class);

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showAddressBookForm(Model model) {
        LOGGER.debug("Show create new addressBookForm");

        AddressBookFormData addressBookFormData = new AddressBookFormData();
        addressBookFormData.setUsers(new LinkedHashMap<>());
        // TODO: All users in options is performance problem
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            String fio = user.getFullName();
            addressBookFormData.getUsers().put(user.getId(), fio);
		}
        model.addAttribute("addressBookFormData", addressBookFormData);
        model.addAttribute("addressBookForm", new AddressBookForm());
        return "addressBookForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String submitAddressBookForm(@Valid AddressBookForm addressBookForm, BindingResult result, Model model) {
        LOGGER.debug("submit create AddressBookForm: " + String.valueOf(addressBookForm));

        if (!result.hasErrors()) {
            try {
                Long id = addressBookService.createAddress(mapAddressBookFormToAddressBook(addressBookForm));

                model.addAttribute("successAdd", true);
            } catch (IllegalAccessError e) {
                LOGGER.debug("exception map addressBookForm: " + e.getMessage());
            }
        } else {
            LOGGER.debug("submit MessageForm error: " + result.toString());
        }

        AddressBookFormData addressBookFormData = new AddressBookFormData();
        addressBookFormData.setUsers(new LinkedHashMap<>());

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            String fio = user.getFullName();
            addressBookFormData.getUsers().put(user.getId(), fio);
        }
        model.addAttribute("addressBookFormData", addressBookFormData);

        return "addressBookForm";
    }

    private AddressBook mapAddressBookFormToAddressBook(AddressBookForm addressBookForm) throws IllegalAccessError {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == "anonymousUser") {
            throw new IllegalAccessError("Not authenticated user");
        }

        try {
            AddressBook address = new AddressBook();

            User user = userService.getUserByLogin(authentication.getName());
            address.setFromUser(user);
            address.setToUser(new User(Long.valueOf(addressBookForm.getUserTo())));

            return address;

        } catch (NoResultException e){
            throw new IllegalAccessError("Not found user name=" + authentication.getName() + ", exception " + e.getMessage());
        }
    }

    @RequestMapping(value = "/remove/{addressId}", method = RequestMethod.GET)
    public String removeAddress(@PathVariable String addressId) {
        LOGGER.debug("Remove address: id=" + Long.valueOf(addressId));

        addressBookService.removeAddress(Long.valueOf(addressId));

        return "empty";
    }

    @RequestMapping(method = RequestMethod.GET)
    public void getAddressBook() {

    }

    // List for grid
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public @ResponseBody ListDataModel<AddressBookRowModel> listAddressBook(
            Integer page, Integer rows, String sidx, String sord) {
        LOGGER.debug("list AddressBook");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Select only current authentication user
        User user = new User();
        if (authentication != null && authentication.isAuthenticated() && authentication.getName() != "anonymousUser") {
            user = userService.getUserByLogin(authentication.getName());
        }

        List<AddressBook> result = addressBookService.getList(page, rows, sidx, sord, user);

        // Rows elements address book for grid
        ArrayList<AddressBookRowModel> list = new ArrayList<>();

        for (AddressBook row : result) {
            User toUser = row.getToUser();
            String nameUser = toUser.getFullName();
            AddressBookRowModel model = new AddressBookRowModel(row.getId().toString(), nameUser);
            list.add(model);
        }

        Long count = addressBookService.getCountAddressesByUser(user);

        // Calculate totalPages for grid
        Long totalPages = Long.valueOf("0");
        if (Long.compare(count, 0) > 0) {
            Double total = (Double) Math.ceil( (double) count / rows );
            totalPages = total.longValue();
        }

        ListDataModel<AddressBookRowModel> data = new ListDataModel<>();
        data.setRows(list);
        data.setPage(Long.valueOf(page));
        data.setTotal(totalPages);
        data.setRecords(count);

        LOGGER.debug("addressBookDataModel: " + data.toString());

        return data;
    }
}
