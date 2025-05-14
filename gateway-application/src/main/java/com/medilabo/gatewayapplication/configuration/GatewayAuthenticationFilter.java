package com.medilabo.gatewayapplication.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @Value("${authentication-token-validation-uri}")
    private String authenticationValidationUri;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Signal Filter");
        if (!exchange.getRequest().getURI().getPath().contains("/authentication")) {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                String message = "Please authenticate before accessing any resource";
                byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bytes);
                return response.writeWith(Mono.just(buffer));
            }

            String authenticationHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).getFirst();
            if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
                authenticationHeader = authenticationHeader.substring(7);
            }

            try {
                boolean isValid = Boolean.TRUE.equals(restTemplate.getForObject(String.format(authenticationValidationUri, authenticationHeader), Boolean.class));
                log.info("Token Validity " + isValid);
            } catch (Exception e) {
                log.info("Invalid Token: " + e.getMessage());
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return response.writeWith(Mono.just(response.bufferFactory().wrap("INVALID TOKEN".getBytes())));
            }
        }
        return chain.filter(exchange);
    }

}
