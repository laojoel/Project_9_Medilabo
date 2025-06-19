package com.medilabo.gatewayapplication.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class AuthenticationRouting {

    @Value("${authentication-application-uri}")
    private String authenticationUri;

    @Bean
    public RouteLocator authenticationRouterLocator(RouteLocatorBuilder builder, GatewayAuthenticationFilter filter) {
        return builder
                .routes()
                .route(r -> r.path("/authentication").filters(f -> f.filter(filter)).uri(authenticationUri))
                .route(r -> r.path("/authentication/validate").filters(f -> f.filter(filter)).uri(authenticationUri))
                .build();
    }

}
