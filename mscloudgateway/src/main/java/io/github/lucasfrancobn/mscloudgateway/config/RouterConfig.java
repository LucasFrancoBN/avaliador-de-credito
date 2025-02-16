package io.github.lucasfrancobn.mscloudgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                // redireciona qualquer uri que tenha clientes para o load balancer redirecionar para algum msclientes
                .route(r -> r.path("/clientes/**")
                        .uri("lb://msclientes"))
                .route(r -> r.path("/cartoes/**")
                        .uri("lb://mscartoes"))
                .route(r -> r.path("/avaliacoes-credito/**")
                        .uri("lb://msavaliadorcredito"))
                .build();
    }
}
