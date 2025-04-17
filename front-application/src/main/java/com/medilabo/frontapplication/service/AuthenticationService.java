package com.medilabo.frontapplication.service;

import com.medilabo.frontapplication.context.SessionContext;
import com.medilabo.frontapplication.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.medilabo.frontapplication.proxy.AuthenticationProxy;
import feign.FeignException;

@Service
public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final AuthenticationProxy authenticationProxy;

    public AuthenticationService(AuthenticationProxy authenticationProxy) {
        this.authenticationProxy = authenticationProxy;
    }

    public void login(String authenticationHeader, User user) {
        try {
            authenticationProxy.login(authenticationHeader);
            //sessionContext.getAuthanticatedUser().setUsername(user.getUsername());
            //sessionContext.getAuthanticatedUser().setPassword(user.getPassword());
        } catch (FeignException e) {
            if (e.status() == 401) {
                log.info("Authentication Failed for user: " + user.getUsername());
                //sessionContext.setMessage("Wrong username or password");
            }
            else {
                log.error("FeignException " + e.status() + ": " + e.getMessage());
            }
        }
        //String urlTempo = sessionContext.getRedirectAfterExceptionUrl();
        //SessionContext.setReturnUrl("redirect:" + urlTempo);
    }

}
