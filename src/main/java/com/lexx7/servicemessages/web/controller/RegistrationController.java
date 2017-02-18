package com.lexx7.servicemessages.web.controller;

import com.lexx7.servicemessages.business.service.UserService;
import com.lexx7.servicemessages.model.entity.User;
import com.lexx7.servicemessages.web.dto.UserForm;
import com.lexx7.servicemessages.web.map.UserMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("registration")
public class RegistrationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
	
	@Autowired
	private UserService userService;
	
    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;

	// Register user
	@RequestMapping(method = RequestMethod.GET)
	public UserForm showRegistrationUserForm() {
		LOGGER.debug("show registration userForm");
		UserForm userForm = new UserForm();
		return userForm;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitRegistrationUserForm(@Valid UserForm userForm, BindingResult result) {
		LOGGER.debug("submit registration UserForm: " + String.valueOf(userForm));
		if (!result.hasErrors()) {
    		User user = UserMap.mapUserFormToUser(userForm);
    		user.setPassword(passwordEncoder.encode(userForm.getPassword()));
            userService.createUser(user);
			return "login";
		} else {
			LOGGER.debug("submitRegistrationUserForm error: " + result.toString());
			return "registration";
		}
	}
}
