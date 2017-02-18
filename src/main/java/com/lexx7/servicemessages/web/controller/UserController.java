package com.lexx7.servicemessages.web.controller;


import javax.validation.Valid;


import com.lexx7.servicemessages.web.dto.UserRoleForm;
import com.lexx7.servicemessages.web.map.UserMap;
import com.lexx7.servicemessages.web.model.ListDataModel;
import com.lexx7.servicemessages.web.model.UserRowModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lexx7.servicemessages.business.service.UserService;
import com.lexx7.servicemessages.model.entity.User;
import com.lexx7.servicemessages.web.dto.UserForm;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @RequestMapping(method = RequestMethod.GET)
    public UserForm showUserForm() {
    	LOGGER.debug("show new userForm");
    	UserForm userForm = new UserForm();
        return userForm;
    }

	@RequestMapping(method = RequestMethod.POST)
    public String submitNewUserForm(@Valid UserForm userForm, BindingResult result) {
    	LOGGER.debug("submitNewUserForm: " + String.valueOf(userForm));
    	if (!result.hasErrors()) {
    		User user = UserMap.mapUserFormToUser(userForm);
    		user.setPassword(passwordEncoder.encode(userForm.getPassword()));
            userService.createUser(user);
            // Redirect to the user after create successful
            return "redirect:/user/control";
    	} else {
    		LOGGER.debug("submitNewUserForm error: " + result.toString());
    		return "user";
    	}
    }

    // Edit role
	@RequestMapping(value="/role/{userId}", method = RequestMethod.GET)
	public String showUserRoleForm(@PathVariable long userId, Model model) {
		LOGGER.debug("show existed userRoleForm");
		UserRoleForm userRoleForm = UserMap.mapUserToUserRoleForm(userService.getUser(userId));
		model.addAttribute("userRoleForm", userRoleForm);
		return "userRole";
	}
    
    @RequestMapping(value="/role/{userId}", method = RequestMethod.POST)
    public String submitExistedUserRoleForm(@PathVariable long userId, @Valid UserRoleForm userRoleForm,
											BindingResult result, Model model) {
    	LOGGER.debug("submitExistedUserRoleForm: " + String.valueOf(userRoleForm));
    	if (!result.hasErrors()) {
    		userService.changeRole(Long.valueOf(userRoleForm.getId()), userRoleForm.isAdmin());
			model.addAttribute("successReplace", true);
    	} else {
    		LOGGER.debug("submitExistedUserRoleForm error: " + result.toString());
    	}
        return "userRole";
    }

	@RequestMapping(value = "control")
	private String getList() {
    	LOGGER.debug("getList");

    	return "users";
	}

	// List for grid
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody ListDataModel<UserRowModel> listUsers(Integer page, Integer rows, String sidx, String sord) {
		LOGGER.debug("list Users");

		List<User> result = userService.getList(page, rows, sidx, sord);

		// Rows elements message for grid
		ArrayList<UserRowModel> list = new ArrayList<>();

		for (User row : result) {
			String nameUser = row.getLastName() + " " + row.getFirstName() + " " + row.getMiddleName();
			UserRowModel model = new UserRowModel();
			model.setId(row.getId().toString());
			model.setFio(nameUser);
			model.setEmail(row.getEmail());
			model.setLogin(row.getLogin());
			model.setRole(row.isAdmin() ? "Администратор" : "Пользователь");
			list.add(model);
		}

		Long count = userService.getCountUsers();

		// Calculate totalPages for grid
		Long totalPages = Long.valueOf("0");
		if (Long.compare(count, 0) > 0) {
			Double total = (Double) Math.ceil( (double) count / rows );
			totalPages = total.longValue();
		}

		ListDataModel<UserRowModel> data = new ListDataModel<>();
		data.setRows(list);
		data.setPage(Long.valueOf(page));
		data.setTotal(totalPages);
		data.setRecords(count);

		LOGGER.debug("UserDataModel: " + data.toString());

		return data;
	}

	@RequestMapping(value = "/remove/{userId}", method = RequestMethod.GET)
	public String removeUser(@PathVariable String userId) {
		LOGGER.debug("Remove user: id=" + Long.valueOf(userId));

		userService.removeUser(Long.valueOf(userId));

		return "empty";
	}
}
