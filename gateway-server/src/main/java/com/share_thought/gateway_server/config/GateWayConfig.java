package com.share_thought.gateway_server.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

@Configuration

public class GateWayConfig {

    @Bean
    public RouteLocator routeBuilder(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ThoughtsSync", predicateSpec -> predicateSpec
                        .path("/api/v1/thoughts-sync", "/api/v1/thoughts-sync/**")
                        .filters(f -> f
                                .rewritePath("/api/v1/thoughts-sync(?<segment>/?.*)", "/chats${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .addResponseHeader("X-API-Version", "1.0"))
                        .uri("lb://thoughts-sync"))
                .route("UserManager", predicateSpec -> predicateSpec
                        .path("/api/v1/user-manager", "/api/v1/user-manager/**")
                        .filters(f -> f
                                .rewritePath("/api/v1/user-manager(?<segment>/?.*)", "/users${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .addResponseHeader("X-API-Version", "1.0"))
                        .uri("lb://user-manager"))
                .route("ThoughtHub", predicateSpec -> predicateSpec
                        .path("/api/v1/thought-hub", "/api/v1/thought-hub/**")
                        .filters(f -> f
                                .rewritePath("/api/v1/thought-hub(?<segment>/?.*)", "/groups${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .addResponseHeader("X-API-Version", "1.0"))
                        .uri("lb://thought-hub"))

                .route("ThoughStore", predicateSpec -> predicateSpec
                        .path("/api/v1/thought-store", "/api/v1/thought-store/**")
                        .filters(f -> f
                                .rewritePath("/api/v1/thought-store(?<segment>/?.*)", "/messages${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .addResponseHeader("X-API-Version", "1.0"))
                        .uri("lb://thought-store"))
                .build();
    }
}
