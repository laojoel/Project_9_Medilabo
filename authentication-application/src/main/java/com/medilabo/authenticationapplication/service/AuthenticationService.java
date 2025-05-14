package com.medilabo.authenticationapplication.service;

import com.medilabo.authenticationapplication.security.JwtProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtProcessor jwtService;

    public String generateToken(String username) {
        log.info("generating token");
        return jwtService.generateToken(username);
    }

    public boolean validateToken(String token) {
        log.info("validating token ...");
        return jwtService.validateToken(token);
    }
}
