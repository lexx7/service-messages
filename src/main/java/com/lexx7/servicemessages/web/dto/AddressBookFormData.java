package com.lexx7.servicemessages.web.dto;

import java.util.Map;

public class AddressBookFormData {
	
	private Map<Long, String> users;

	public Map<Long, String> getUsers() {
		return users;
	}

	public void setUsers(Map<Long, String> users) {
		this.users = users;
	}

}
