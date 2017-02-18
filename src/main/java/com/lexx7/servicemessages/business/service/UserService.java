package com.lexx7.servicemessages.business.service;

import java.util.List;

import com.lexx7.servicemessages.model.entity.User;

public interface UserService {
	
	List<User> getAllUsers();
	
	Long createUser(User user);
	
	void updateUser(User user);

	void removeUser(Long id);
	
	User getUser(Long id);
	
	User getUserByLogin(String login);

	boolean changePwd(Long id, String password);

	boolean changeRole(Long id, Boolean admin);

	List<User> getList(Integer page, Integer limit, String sidx, String sord);

	Long getCountUsers();
}
