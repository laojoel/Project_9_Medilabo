package com.medilabo.riskapplication.security;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("\n\n\n ----------------------------------------------");
        System.out.println("INCOMING REQUEST (bearer Received) = " + authorizationHeader);

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            //String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            request.getSession().setAttribute("token", authorizationHeader); // Store token in HttpSession
            log.info("Token stored in HttpSession: {}", authorizationHeader);
        } else {
            log.warn("Authorization header is missing or invalid");
        }

        return true;
    }
}