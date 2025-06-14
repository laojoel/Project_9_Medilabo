package com.medilabo.gatewayapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PatientRouting {

    @Value("${patient-uri}patients")
    private String patientAllUri;
    @Value("${patient-uri}patients/view/1")
    private String patientViewUri;
    @Value("${patient-uri}patients/create")
    private String patientCreateUri;
    @Value("${patient-uri}patients/update")
    private String patientUpdateUri;
    @Value("${patient-uri}patients/delete")
    private String patientDeleteUri;

    @Bean
    public RouteLocator patientRouterLocator(RouteLocatorBuilder builder, GatewayAuthenticationFilter filter) {
        return builder
                .routes()
                .route(r -> r.path("/patients").filters(f -> f.filter(filter)).uri(patientAllUri))
                .route(r -> r.path("/patients/view/{id}").filters(f -> f.filter(filter)).uri(patientViewUri))
                .route(r -> r.path("/patients/create").filters(f -> f.filter(filter)).uri(patientCreateUri))
                .route(r -> r.path("/patients/update/{id}").filters(f -> f.filter(filter)).uri(patientUpdateUri))
                .route(r -> r.path("/patients/delete/{id}").filters(f -> f.filter(filter)).uri(patientDeleteUri))
                .build();
    }

}
