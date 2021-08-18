package com.majka.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIGatewayConfiguration {

    @Bean
    public RouteLocator getewayRouter(RouteLocatorBuilder builder){
//        Function<PredicateSpec, Buildable<Route>> routeFuntion =
//                p -> p.path("/get")
//                        .filters(f -> f
//                                .addRequestHeader("My Header", "MyURI")
//                                .addRequestParameter("My Param", "My Value"))
//
//                        .uri("http://httpbin.org:80");
        return builder.routes()
                .route(p -> p.path("/get")
                        .filters(f -> f
                                .addRequestHeader("My Header", "MyURI")
                                .addRequestParameter("My Param", "My Value"))
                        .uri("http://httpbin.org:80"))

                .route(p -> p.path("/currency-exchange/**")
                                .uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))

                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath(
                                "/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}"
                        ))
                        .uri("lb://currency-conversion"))
                .build();
    }
}
