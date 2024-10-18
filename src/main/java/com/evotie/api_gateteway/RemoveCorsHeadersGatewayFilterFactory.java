package com.evotie.api_gateteway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class RemoveCorsHeadersGatewayFilterFactory extends AbstractGatewayFilterFactory<RemoveCorsHeadersGatewayFilterFactory.Config> {

    public RemoveCorsHeadersGatewayFilterFactory() {
        super(Config.class);
    }

    public static class Config {
        // Configuration properties if needed
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            exchange.getResponse().getHeaders().remove("Access-Control-Allow-Origin");
            exchange.getResponse().getHeaders().remove("Access-Control-Allow-Credentials");
            // Add other headers you want to remove here
            return chain.filter(exchange);
        };
    }
//
//    @Override
//    public String getName() {
//        return "RemoveCorsHeaders";
//    }
}
