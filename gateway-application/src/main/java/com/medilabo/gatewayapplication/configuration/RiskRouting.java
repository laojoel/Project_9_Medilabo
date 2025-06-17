package com.medilabo.gatewayapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RiskRouting {

    @Value("${risk-uri}")
    private String riskUri;
}
