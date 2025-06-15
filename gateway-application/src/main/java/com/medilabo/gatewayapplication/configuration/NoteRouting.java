package com.medilabo.gatewayapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoteRouting {

    @Value("${note-uri}notes/1")
    private String noteAllUri;
    @Value("${note-uri}notes/view/1")
    private String noteViewUri;
    @Value("${note-uri}notes/create")
    private String noteCreateUri;
    @Value("${note-uri}notes/update")
    private String noteUpdateUri;
    @Value("${note-uri}notes/delete")
    private String noteDeleteUri;

    @Bean
    public RouteLocator noteRouterLocator(RouteLocatorBuilder builder, GatewayAuthenticationFilter filter) {
        return builder
                .routes()
                .route(r -> r.path("/notes/{patId}").filters(f -> f.filter(filter)).uri(noteAllUri))
                .route(r -> r.path("/notes/view/{id}").filters(f -> f.filter(filter)).uri(noteViewUri))
                .route(r -> r.path("/notes/create").filters(f -> f.filter(filter)).uri(noteCreateUri))
                .route(r -> r.path("/notes/update/{id}").filters(f -> f.filter(filter)).uri(noteUpdateUri))
                .route(r -> r.path("/notes/delete/{id}").filters(f -> f.filter(filter)).uri(noteDeleteUri))
                .build();
    }

}
