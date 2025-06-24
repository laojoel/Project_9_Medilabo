package com.medilabo.frontapplication.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    /*
    Verify that all incoming clients request have a Token
    At the exemption of the "login page" route which is public
    */

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (request.getServletPath().contains("/loginPage")) {
            log.info("Login Page -> no token required");
            return true;
        }
        if (!StringUtils.hasText((String)request.getSession().getAttribute("token"))) {
            log.warn("Missing token");
            response.sendRedirect("/loginPage");
            return false;
        }
        log.info("Token found");
        return true;
    }
}