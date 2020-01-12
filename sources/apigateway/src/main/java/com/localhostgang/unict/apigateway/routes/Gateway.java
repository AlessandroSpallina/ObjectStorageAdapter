package com.localhostgang.unict.apigateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Gateway {

    @Value("${FMS_HOST:filemanagementservice}")
    private String host;

    @Value("${FMS_PORT:8080}")
    private String port;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(predicateSpec -> predicateSpec
                        .path("/")
                        .filters(f-> f
                            .addRequestHeader("Hello", "World"))
                        .uri("http://"+"172.19.0.6"+":"+port)
                )
                .build();
    }

}
