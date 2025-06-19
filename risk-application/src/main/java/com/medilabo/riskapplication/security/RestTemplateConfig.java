package com.medilabo.riskapplication.security;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RestTemplateConfig {

    @Bean("restTemplate")
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestScope
    @Bean("authRestTemplate")
    RestTemplate authRestTemplate(HttpSession session) {
        String token = (String) session.getAttribute("token");
        log.info("Bearer token to send to downstream services: " + token);
        return new RestTemplateBuilder(rt -> rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", token);
            return execution.execute(request, body);
        })).build();
    }
}
