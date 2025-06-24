package com.medilabo.gatewayapplication.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class GatewayAuthenticationFilterTest {

    @InjectMocks
    private GatewayAuthenticationFilter gatewayAuthenticationFilter;

    @Mock
    private RestTemplate restTemplate;

    @Value("${authentication-application-token-validation-uri}")
    private String authenticationValidationUri = "http://localhost:8080/validate/%s";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Should return 'Unauthorized' code because the request doesn't have any authorization header.
    @Test
    void testFilter_NoAuthorizationHeader() {
        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/test").build());
        ServerHttpResponse response = exchange.getResponse();

        gatewayAuthenticationFilter.filter(exchange, chain -> Mono.empty())
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    // Valid Token,request is allowed to continue down stream
    @Test
    void testFilter_ValidToken() {
        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/test")
                .header(HttpHeaders.AUTHORIZATION, "Bearer validToken")
                .build());

        when(restTemplate.getForObject(eq(String.format(authenticationValidationUri, "validToken")), eq(Boolean.class)))
                .thenReturn(true);

        gatewayAuthenticationFilter.filter(exchange, chain -> Mono.empty())
                .as(StepVerifier::create)
                .verifyComplete();

        ServerHttpRequest request = exchange.getRequest();
        assertEquals("Bearer validToken", request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    // Invalid Token, should return 'Forbidden' code
    @Test
    void testFilter_InvalidToken() {
        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/test")
                .header(HttpHeaders.AUTHORIZATION, "Bearer invalidToken")
                .build());

        when(restTemplate.getForObject(eq(String.format(authenticationValidationUri, "invalidToken")), eq(Boolean.class)))
                .thenReturn(false);

        ServerHttpResponse response = exchange.getResponse();

        gatewayAuthenticationFilter.filter(exchange, chain -> Mono.empty())
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }


    //Authentication Request skips the whole checking steps and continue down-stream
    @Test
    void testFilter_AuthenticationRequest() {
        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/authentication/test").build());

        gatewayAuthenticationFilter.filter(exchange, chain -> Mono.empty())
                .as(StepVerifier::create)
                .verifyComplete();
    }
}
