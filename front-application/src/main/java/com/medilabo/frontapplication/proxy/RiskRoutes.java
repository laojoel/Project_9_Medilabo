package com.medilabo.frontapplication.proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class RiskRoutes {
    @Value("${risks-uri}")
    private String riskUri;
}
