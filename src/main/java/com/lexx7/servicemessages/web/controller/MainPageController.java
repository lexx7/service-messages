package com.lexx7.servicemessages.web.controller;

import com.lexx7.servicemessages.web.map.UserMap;
import com.lexx7.servicemessages.web.dto.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lexx7.servicemessages.business.service.UserService;
import com.lexx7.servicemessages.model.entity.User;

import javax.validation.Valid;

@Controller
@RequestMapping("main")
public class MainPageController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainPageController.class); 


	@RequestMapping(method = RequestMethod.GET)
	public void getData() {
		LOGGER.debug("show main");
	}

}
