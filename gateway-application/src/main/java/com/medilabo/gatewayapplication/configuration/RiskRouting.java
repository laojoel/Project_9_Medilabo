package com.medilabo.gatewayapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RiskRouting {

    @Value("${risk-application-uri}")
    private String riskUri;

    @Bean
    public RouteLocator riskRouterLocator(RouteLocatorBuilder builder, GatewayAuthenticationFilter filter) {
        return builder
                .routes()
                .route(r -> r.path("/risk/{id}").filters(f -> f.filter(filter)).uri(riskUri))
                .build();
    }
}
