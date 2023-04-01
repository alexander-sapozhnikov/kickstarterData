package com.sapozhnikov.kickstarterData.controller;

import com.sapozhnikov.kickstarterData.entity.Role;
import com.sapozhnikov.kickstarterData.entity.User;
import com.sapozhnikov.kickstarterData.repository.UserRepository;
import com.sapozhnikov.kickstarterData.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String addUser(User user, Map<String, Object> model) {
        String textError = registrationService.createUser(user);
        if (textError == null){
            return "login";
        } else {
            model.put("message", textError);
            return "registration";
        }
    }
}
