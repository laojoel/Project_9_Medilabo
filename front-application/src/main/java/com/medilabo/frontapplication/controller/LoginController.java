package com.medilabo.frontapplication.controller;


import com.medilabo.frontapplication.configuration.GatewayProperties;
import com.medilabo.frontapplication.model.LoginForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
public class LoginController {

    private final RestTemplate      restTemplate;
    private final GatewayProperties properties;

    public LoginController(@Qualifier("restTemplate") RestTemplate restTemplate, GatewayProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @GetMapping({"/","/login"})
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "loginPage";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute LoginForm loginForm, HttpServletRequest request) {
        log.info("Process login for username: " + loginForm.getUsername());
        String accessToken = restTemplate.postForObject(properties.getAuthenticationUri(), loginForm, String.class);
        log.info("Access Token Generated " + accessToken);
        HttpSession session = request.getSession();
        assert accessToken != null;
        session.setAttribute("token", accessToken);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/loginPage";
    }

}
