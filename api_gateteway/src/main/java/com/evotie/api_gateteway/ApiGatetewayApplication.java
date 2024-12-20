package com.evotie.api_gateteway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatetewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatetewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("keycloak_route", r -> r
						.path("/auth/**", "/resources/**")
						.filters(f -> f
								.rewritePath("/auth/(?<segment>.*)", "/${segment}")
								.filter(new RemoveCorsHeadersGatewayFilterFactory().apply(new RemoveCorsHeadersGatewayFilterFactory.Config())))
						.uri("http://localhost:8086"))
				.route("registration_ms", r -> r
						.path("/voter-registration/**","/gramaniladhari/**", "/verification_officer/**", "/voter/**")
						.filters(f -> f
								.filter(new RemoveCorsHeadersGatewayFilterFactory().apply(new RemoveCorsHeadersGatewayFilterFactory.Config())))
						.uri("http://localhost:8081"))
				.route("FILE-MS", r -> r
						.path("/api/files/**")
						.filters(f -> f
								.filter(new RemoveCorsHeadersGatewayFilterFactory().apply(new RemoveCorsHeadersGatewayFilterFactory.Config())))
						.uri("http://localhost:5009"))
				.route("ELECTION-MS", r -> r
						.path("/election/**")
						.filters(f -> f
								.filter(new RemoveCorsHeadersGatewayFilterFactory().apply(new RemoveCorsHeadersGatewayFilterFactory.Config())))
						.uri("http://localhost:8083"))
				.route("ELECTION-MS", r -> r
						.path("/election/**")
						.filters(f -> f
								.filter(new RemoveCorsHeadersGatewayFilterFactory().apply(new RemoveCorsHeadersGatewayFilterFactory.Config())))
						.uri("http://localhost:8083"))
				.build();
	}

}
