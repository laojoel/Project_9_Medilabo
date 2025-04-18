package com.medilabo.frontapplication.service;

import com.medilabo.frontapplication.context.SessionContext;
import com.medilabo.frontapplication.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.medilabo.frontapplication.proxy.AuthenticationProxy;
import feign.FeignException;

import java.util.Base64;

@Service
public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final AuthenticationProxy authenticationProxy;

    public AuthenticationService(AuthenticationProxy authenticationProxy) {
        this.authenticationProxy = authenticationProxy;
    }

    public void login(User user) {
        byte[] encodedBytes = Base64.getEncoder().encode((user.getUsername() + ":" + user.getPassword()).getBytes());
        String credential = "Basic " + new String(encodedBytes);

        try {
            authenticationProxy.login(credential);
            user.setAuthenticated(true);
        } catch (FeignException e) {
            user.setAuthenticated(false);
            if (e.status() == 401) {
                log.info("Authentication Failed for user: " + user.getUsername());
                user.setMessage("Wrong username or password");
            }
            else {
                log.error("FeignException " + e.status() + ": " + e.getMessage() + " for Login " + user.getUsername());
                user.setMessage("Error " + e.status() + ". Please reach out our support for further assistance");
            }
        }
    }

}
