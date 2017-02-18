package com.lexx7.servicemessages.business.service;


import com.lexx7.servicemessages.model.entity.AddressBook;
import com.lexx7.servicemessages.model.entity.User;

import java.util.List;

public interface AddressBookService {

    Long createAddress(AddressBook message);

    void removeAddress(Long id);

    AddressBook getAddress(Long id);

    List<AddressBook> getList(Integer page, Integer limit, String sidx, String sord, User user);

    Long getCountAddressesByUser(User user);

    AddressBook getAddressByToUserAndFromUser(User toUser, User fromUser);
}
