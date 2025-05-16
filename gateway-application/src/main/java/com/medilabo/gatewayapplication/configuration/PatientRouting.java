package com.medilabo.gatewayapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PatientRouting {

    @Value("${patient-all-uri}")
    private String patientAllUri;

    @Value("${patient-uri}patients/view/1")
    private String patientUri;

    @Bean
    public RouteLocator patientRouterLocator(RouteLocatorBuilder builder, GatewayAuthenticationFilter filter) {
        return builder
                .routes()
                .route(r -> r.path("/patients").filters(f -> f.filter(filter)).uri(patientAllUri))
                .route(r -> r.path("/patients/view/{id}").filters(f -> f.filter(filter)).uri(patientUri))
                //.route(r -> r.path("/patients/view/{id}").filters(f -> f.rewritePath("/patients/view/(?<id>.*)", "/patients/view/${id}").filter(filter)).uri(patientUri))
                .build();
    }

}
