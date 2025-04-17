package com.medilabo.frontapplication.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "gateway-application", url = "${gateway-application.url}", contextId = "authentication")
public interface AuthenticationProxy {

    @GetMapping("/authentication/login")
    void login(@RequestHeader("Authorization") String authenticationRequest);
}
