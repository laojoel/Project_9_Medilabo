package com.medilabo.gatewayapplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        System.out.println("CTRL OK");
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
