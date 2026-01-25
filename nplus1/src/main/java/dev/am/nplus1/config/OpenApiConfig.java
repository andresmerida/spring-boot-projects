package dev.am.nplus1.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("N+1 Problem API")
                        .description("A simple API to demonstrate N+1 problem")
                        .version("0.0.1-SNAPSHOT")
                );
    }
}
