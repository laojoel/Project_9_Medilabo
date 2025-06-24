package com.medilabo.gatewayapplication.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
@Slf4j
public class GatewayAuthenticationFilter implements GatewayFilter {
    private final RestTemplate restTemplate;

    public GatewayAuthenticationFilter(@Qualifier("restTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${authentication-application-token-validation-uri}")
    private String authenticationValidationUri;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 1) Verification for all request, except the Authentications ones itself.
        if (!exchange.getRequest().getURI().getPath().contains("/authentication")) {

            // 2) Block the request and ask for authentication first if authorization request header is absent
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                String message = "Please authenticate before accessing any resource";
                byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bytes);
                // blocked, send back 'Unauthorized' code
                return response.writeWith(Mono.just(buffer));
            }

            // 3) remove the 'Bearer' prefix to isolate the value
            String authenticationHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).getFirst();
            if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
                authenticationHeader = authenticationHeader.substring(7);
            }

            try {
                // 4) Call the Authentication Application to verify if the Token is valid
                boolean isValid = Boolean.TRUE.equals(restTemplate.getForObject(String.format(authenticationValidationUri, authenticationHeader), Boolean.class));
                log.info("Token Validity is " + isValid);

                if (isValid) {
                    // 5A) Forward the token to back-end, so it could pass back across the gateway if
                    // it needs to call subsequents applications.
                    ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationHeader)
                            .build();

                    // SUCCESS | Only Authenticated request reach this point and continue down-stream
                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                }

            } catch (Exception e) {
                // 5B) Block the request because the authentication application
                log.info("Invalid Token: " + e.getMessage());
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.FORBIDDEN);
                // Blocked, send back 'forbidden' code
                return response.writeWith(Mono.just(response.bufferFactory().wrap("INVALID TOKEN".getBytes())));
            }
        }
        // Only authentication requests reach this point
        return chain.filter(exchange);

    }

}
