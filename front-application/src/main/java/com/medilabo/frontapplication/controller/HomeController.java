package com.medilabo.frontapplication.controller;

import com.medilabo.frontapplication.model.User;
import com.medilabo.frontapplication.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private final AuthenticationService authenticationService;

    public HomeController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/home")
    public String home(@CookieValue(value = "UID", defaultValue = "") String uid) {
        System.out.println("home | cookie UID = " +  uid);
        return "home";
    }


}
