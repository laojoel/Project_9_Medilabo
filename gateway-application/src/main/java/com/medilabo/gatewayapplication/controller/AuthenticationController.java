package com.medilabo.gatewayapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public Mono<String> login() {
        return Mono.just("login");
    }

    @GetMapping("/home")
    public Mono<String> home(@CookieValue(value = "UID", defaultValue = "") String uid) {
        System.out.println("home | cookie UID = " +  uid);
        return Mono.just("home");
    }
}
