package com.medilabo.gatewayapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoteRouting {

    @Value("${note-application-uri}/")
    private String noteUri;
    @Value("${note-application-uri}/patId")
    private String patIdNotesUri;
    @Value("${note-application-uri}/create")
    private String noteCreationUri;
    @Value("${note-application-uri}/modify")
    private String noteModificationUrl;
    @Value("${note-application-uri}/delete")
    private String noteDeletionUri;

    @Bean
    public RouteLocator noteRouterLocator(RouteLocatorBuilder builder, GatewayAuthenticationFilter filter) {
        return builder
                .routes()
                .route(r -> r.path("/note/{id}").filters(f -> f.filter(filter)).uri(noteUri))
                .route(r -> r.path("/note/patId/{patId}").filters(f -> f.filter(filter)).uri(patIdNotesUri))
                .route(r -> r.path("/note/create").filters(f -> f.filter(filter)).uri(noteCreationUri))
                .route(r -> r.path("/note/modify").filters(f -> f.filter(filter)).uri(noteModificationUrl))
                .route(r -> r.path("/note/delete/{id}").filters(f -> f.filter(filter)).uri(noteDeletionUri))
                .build();
    }

}
