package com.microservices.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ms-companies", r -> r
                        .path("/companies/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("companies-cb")
                                        .setStatusCodes(Set.of("500", "503", "504", "404", "408"))
                                        .setFallbackUri("forward:/companies-fallback/company/fallback")
                                )
                        )
                        .uri("lb://ms-companies"))
                .route("ms-companies-fallback", r -> r
                        .path("/companies-fallback/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://ms-companies-fallback"))
                .route("ms-report", r -> r.path("/report/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://ms-report"))
                .build();
    }

}
