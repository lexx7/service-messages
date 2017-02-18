package com.lexx7.servicemessages.web.controller;


import com.lexx7.servicemessages.business.service.UserService;
import com.lexx7.servicemessages.web.dto.ReplacePasswordUserForm;
import com.lexx7.servicemessages.web.map.UserMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("replace-password")
public class ReplacePasswordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReplacePasswordController.class);

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    // Replace password user

    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public String showReplacePasswordUserForm(@PathVariable long userId, Model model) {
        LOGGER.debug("show ReplacePasswordUserForm");

        ReplacePasswordUserForm replaceForm = UserMap.mapToUserReplacePasswordUserForm(userService.getUser(userId));
        model.addAttribute("replacePasswordUserForm", replaceForm);
        return "userReplacePassword";
    }

    @RequestMapping(value="{userId}", method = RequestMethod.POST)
    public String submitReplacePasswordUserForm(@PathVariable long userId,
                                                @Valid ReplacePasswordUserForm replaceForm,
                                                BindingResult result,
                                                Model model) {

        LOGGER.debug("submit ReplacePasswordUserForm: " + replaceForm.toString());
        if (!result.hasErrors()) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && authentication.getName() != "anonymousUser"
                    && Long.valueOf(replaceForm.getId()).equals(userId)) {

                userService.changePwd(Long.valueOf(replaceForm.getId()), passwordEncoder.encode(replaceForm.getPassword()));
                model.addAttribute("successReplace", true);
            } else {
                LOGGER.debug("Error: replace password not current user");
            }
        } else {
            LOGGER.debug("submit ReplacePasswordUserForm error: " + result.toString());
        }

        return "userReplacePassword";
    }
}
