package com.example.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class RewriteLocationHeaderFilter {

    @Bean
    public GlobalFilter globalFilter() {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();

            if (response.getHeaders().containsKey(HttpHeaders.LOCATION)) {
                List<String> locations = response.getHeaders().get(HttpHeaders.LOCATION);
                if (locations != null && !locations.isEmpty()) {

                    String newLocation = locations.getFirst()
                        .replace("http://auth:9000", "http://localhost:8085")
                        .replace("http://core:8080", "http://localhost:8085");

                    response.getHeaders().set(HttpHeaders.LOCATION, newLocation);
                }
            }
        }));
    }
}
