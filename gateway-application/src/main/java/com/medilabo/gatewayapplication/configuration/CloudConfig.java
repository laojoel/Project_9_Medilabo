package com.medilabo.gatewayapplication.configuration;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CloudConfig {
    private static final Logger log = LoggerFactory.getLogger(CloudConfig.class);

    @Value("${gateway-application.uri}")
    private String gatewayUri;

    @Value("${patient-application.uri}")
    private String patientUri;

    @Value("${note-application.uri}")
    private String noteUri;

    @Value("${risk-application.uri}")
    private String riskUri;

    @Value("${front-application.uri}")
    private String frontUri;


    @Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        log.info("alloc init gateway routes:\n0 | gateway | " + gatewayUri + "\n1 | patient app | " + patientUri + "\n2 | note app | " + noteUri +
                "\n3 | risk app | " + riskUri +  "\n4 | front app | " + frontUri);

        return builder.routes()
                .route("login_service", r -> r.path("/login/**")
                        .uri("lb://LOGIN-SERVICE"))
                .route(r -> r.path("/front-application/front/**")
                        .filters(f -> f.rewritePath("/patient-application/patient/(?<segment>.*)", "/patient/${segment}"))
                        .uri(frontUri))
                .route(r -> r.path("/patient-application/patient/**")
                        .filters(f -> f.rewritePath("/patient-application/patient/(?<segment>.*)", "/patient/${segment}"))
                        .uri(patientUri))
                .route(r -> r.path("/authentication/login")
                        .filters(f -> f.rewritePath("/authentication/login", "/login"))
                        .uri(gatewayUri))
                .route(r -> r.path("/note-application/note/**")
                        .filters(f -> f.rewritePath("/note-application/note/(?<segment>.*)", "/note/${segment}"))
                        .uri(noteUri))
                .route(r -> r.path("/risk-application/risk/**")
                        .filters(f -> f.rewritePath("/risk-application/risk/(?<segment>.*)", "/risk/${segment}"))
                        .uri(riskUri))
                .build();
    }
}
