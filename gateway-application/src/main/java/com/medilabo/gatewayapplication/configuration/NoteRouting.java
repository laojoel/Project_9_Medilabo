package com.medilabo.gatewayapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoteRouting {


    @Value("${note-uri}notes/view/1")
    private String noteUri;

    @Bean
    public RouteLocator noteRouterLocator(RouteLocatorBuilder builder, GatewayAuthenticationFilter filter) {
        return builder
                .routes()
                .route(r -> r.path("/notes/view/{id}").filters(f -> f.filter(filter)).uri(noteUri)).build();
    }

}
