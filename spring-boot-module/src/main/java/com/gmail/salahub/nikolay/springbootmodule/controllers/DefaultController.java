package com.gmail.salahub.nikolay.springbootmodule.controllers;

import com.gmail.salahub.nikolay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    private UserService userService;

    @Autowired
    public DefaultController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }
}
