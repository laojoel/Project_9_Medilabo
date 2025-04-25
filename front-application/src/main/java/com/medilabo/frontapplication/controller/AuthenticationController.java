package com.medilabo.frontapplication.controller;

import com.medilabo.frontapplication.model.User;
import com.medilabo.frontapplication.service.AuthenticationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import com.medilabo.frontapplication.proxy.AuthenticationProxy;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Base64;

@Controller
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/")
    public String loginForm(Model model) {
        User user = new User();
        user.setUsername("testUser"); user.setPassword("pass");
        model.addAttribute("user", user);
        model.addAttribute("message", "hi");
        return "login";
    }

    @GetMapping("/home")
    public String home(@CookieValue(value = "UID", defaultValue = "") String uid) {
        System.out.println("home | cookie UID = " +  uid);
        return "home";
    }


}
