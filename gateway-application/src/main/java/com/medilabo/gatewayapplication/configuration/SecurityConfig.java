package com.medilabo.gatewayapplication.configuration;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private CloudConfig cloudConfig;
    public SecurityConfig(CloudConfig cloudConfig) {
        this.cloudConfig = cloudConfig;

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/login").permitAll()
                        .anyExchange().authenticated()
                )
                .formLogin(formLoginSpec -> formLoginSpec
                        .loginPage("/login")
                        .authenticationSuccessHandler((WebFilterExchange webFilterExchange, Authentication authentication) -> {
                            ServerWebExchange exchange = webFilterExchange.getExchange();

                            // Get the associated user UID and add it up as a cookie to persist
                            // across the futures transaction.

                            String userUid = authentication.getAuthorities().stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .filter(authority -> authority.startsWith("UID_"))
                                    .map(authority -> authority.substring(4)) // Extract the UID part
                                    .findFirst()
                                    .orElse("");
                            ResponseCookie cookie = ResponseCookie.from("UID", userUid)
                                    .path("/")
                                    .httpOnly(true)
                                    .secure(false) // Set to true if using HTTPS
                                    .build();
                            exchange.getResponse().addCookie(cookie);
                            exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                            exchange.getResponse().getHeaders().setLocation(URI.create("/home"));
                            return exchange.getResponse().setComplete();
                        })
                        .authenticationFailureHandler((webFilterExchange, exception) -> {
                            ServerWebExchange exchange = webFilterExchange.getExchange();
                            exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                            exchange.getResponse().getHeaders().setLocation(URI.create("/login?error=true"));
                            return Mono.empty();
                        })
                )
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint(new RedirectServerAuthenticationEntryPoint("/login")) // Redirect to login page when authentication is required
                )
                .build();
    }

    // fixed in-memory base users
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.builder().username("user")
                .password(passwordEncoder().encode("user"))
                .authorities("UID_123456789")
                .roles("ROLE_USER")
                .build();

        UserDetails admin = User.builder().username("admin")
                .password(passwordEncoder().encode("admin"))
                .authorities("UID_111122223")
                .roles("ROLE_ADMIN")
                .build();

        return new MapReactiveUserDetailsService(user, admin);
    }
}
