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

    /*
     *  DEBUG ONLY FUNCTIONALITY
     *  Allow us to print the entry request URI versus the output forwarded URI
     */

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        return chain.filter(exchange).doOnSuccess(aVoid -> {

            String requestUri = exchange.getRequest().getURI().toString(); // Original request URI
            String forwardUri = exchange.getAttribute("org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayRequestUrl").toString();

            logger.debug("\n Original Request URI: {} = VS = {} \n", requestUri, forwardUri);

            System.out.println(" \n");
        });
    }
}
