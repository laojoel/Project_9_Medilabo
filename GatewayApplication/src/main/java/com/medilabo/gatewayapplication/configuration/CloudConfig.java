package com.medilabo.gatewayapplication.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

public class CloudConfig {
    private static final Logger log = LoggerFactory.getLogger(CloudConfig.class);

    @Value("${gateway-app.uri}")
    private String gatewayUri;

    @Value("${patient-app.uri}")
    private String patientUri;

    @Value("${note-app.uri}")
    private String noteUri;

    @Value("${risk-app.uri}")
    private String riskUri;

    @Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        log.info("alloc init gateway routes: (1) " + gatewayUri + " | (2) " + patientUri + " | (3) " + noteUri +
                " | (4) " + riskUri);

        return builder.routes()
                .route(r -> r.path("/patient-app/patient/**")
                        .filters(f -> f.rewritePath("/patient-app/patient/(?<segment>.*)", "/patient/${segment}"))
                        .uri(patientUri))
                .route(r -> r.path("/auth/login")
                        .filters(f -> f.rewritePath("/auth/login", "/login"))
                        .uri(gatewayUri))
                .route(r -> r.path("/note-app/note/**")
                        .filters(f -> f.rewritePath("/note-app/note/(?<segment>.*)", "/note/${segment}"))
                        .uri(noteUri))
                .route(r -> r.path("/risk-app/risk/**")
                        .filters(f -> f.rewritePath("/risk-app/risk/(?<segment>.*)", "/risk/${segment}"))
                        .uri(riskUri))
                .build();
    }
}
