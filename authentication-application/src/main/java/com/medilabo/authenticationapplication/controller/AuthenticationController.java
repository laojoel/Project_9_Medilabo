package com.medilabo.authenticationapplication.controller;

import com.medilabo.authenticationapplication.model.AuthenticationRequest;
import com.medilabo.authenticationapplication.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    public final AuthenticationService authenticationService;
    private final AuthenticationManager authManager;

    /*
    Generates Token. Requested from the Front application passing across the gateway when the client send
    the correct credentials (username + password on login page).
    */

    @PostMapping
    public ResponseEntity<String> generateToken(@RequestBody AuthenticationRequest authRequest) {
        log.info("Token generation for username: " + authRequest.getUsername());
        if (authManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()))
                .isAuthenticated()) {
            return ResponseEntity.ok(authenticationService.generateToken(authRequest.getUsername()));
        } else {
            throw new RuntimeException("User invalid access ");
        }
    }

    /*
        Used to authenticate a Token
        called by the Gateway when a request is passing on a protected route.
     */

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam("token") String token) {
        try {
            boolean result = authenticationService.validateToken(token);
            log.info("token verification succeeded");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.info("token verification failed: " +e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
        }
    }
}
