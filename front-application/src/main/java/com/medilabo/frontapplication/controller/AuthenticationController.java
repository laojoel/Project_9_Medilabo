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
        model.addAttribute("user", "");
        model.addAttribute("message", "");
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            log.error("Result has error in login");
            return "login";
        }

        //byte[] encodedBytes = Base64.getEncoder().encode((user.getUsername() + ":" + user.getPassword()).getBytes());
        //String authenticationHeader = "Basic " + new String(encodedBytes);

        //authenticationService.login(authenticationHeader, user);

        return "login"; // was return
    }


}
