package com.medilabo.gatewayapplication.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Order(1) // Adjust the order if necessary
@Slf4j
public class LogFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        return chain.filter(exchange).doOnSuccess(aVoid -> {
            // Log the final forward URL
            String requestUri = exchange.getRequest().getURI().toString(); // Original request URI
            String forwardUri = exchange.getAttribute("org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayRequestUrl").toString();

            logger.info("\n|||||||||||||||||| Original Request URI: {}", requestUri);
            logger.info("|||||||||||||||||| Forwarding to URI: {} \n", forwardUri);

            exchange.getAttributes().forEach((key, value) -> log.info("OOOOOOOOOOOOOOOOO Exchange Attribute: {} -> {}", key, value));
            System.out.println(" \n");
        });
    }
}
