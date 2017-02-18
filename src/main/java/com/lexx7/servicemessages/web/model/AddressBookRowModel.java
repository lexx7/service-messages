package com.lexx7.servicemessages.web.model;


public class AddressBookRowModel {

    private String id;

    private String toUser;

    public AddressBookRowModel(String id, String toUser) {
        this.id = id;
        this.toUser = toUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
}
