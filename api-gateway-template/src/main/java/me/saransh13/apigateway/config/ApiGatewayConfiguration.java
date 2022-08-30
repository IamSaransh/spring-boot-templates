package me.saransh13.apigateway.config;

import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.RequiredArgsConstructor;
import me.saransh13.apigateway.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.client.HttpClient;

/**
 * @author saransh
 */
@Configuration
@RequiredArgsConstructor
public class ApiGatewayConfiguration {

    private final AuthenticationFilter authenticationFilter;


    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return builder
                .routes()
                .route(predicateSpec -> predicateSpec
                    .path("/auth/**")
                    .filters(gatewayFilterSpec -> gatewayFilterSpec
                    )
                    .uri("lb://authorization-service")  //http://localhost:8765/authorization-service/auth/v1/login
                )
                .route(predicateSpec -> predicateSpec
                        .path("/process/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .filter(authenticationFilter))
                        .uri("lb://component-processing-service")
                )


                .build();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    }



}
