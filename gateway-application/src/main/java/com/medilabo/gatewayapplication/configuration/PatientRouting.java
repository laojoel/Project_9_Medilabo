package com.medilabo.gatewayapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PatientRouting {

    @Value("${patient-application-uri}/")
    private String patientUri;
    @Value("${patient-application-uri}/all")
    private String allPatientsUri;
    @Value("${patient-application-uri}/create")
    private String patientCreationUri;
    @Value("${patient-application-uri}/modify")
    private String patientModificationUri;
    @Value("${patient-application-uri}/delete")
    private String patientDeletionUri;

    @Bean
    public RouteLocator patientRouterLocator(RouteLocatorBuilder builder, GatewayAuthenticationFilter filter) {
        return builder
                .routes()
                .route(r -> r.path("/patient/{id}").filters         (f -> f.filter(filter)).uri(patientUri))
                .route(r -> r.path("/patient/all").filters          (f -> f.filter(filter)).uri(allPatientsUri))
                .route(r -> r.path("/patient/create").filters       (f -> f.filter(filter)).uri(patientCreationUri))
                .route(r -> r.path("/patient/modify").filters       (f -> f.filter(filter)).uri(patientModificationUri))
                .route(r -> r.path("/patient/delete/{id}").filters  (f -> f.filter(filter)).uri(patientDeletionUri))
                .build();
    }

}
