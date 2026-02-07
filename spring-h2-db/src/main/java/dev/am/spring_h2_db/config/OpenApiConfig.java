package dev.am.spring_h2_db.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title( "Spring H2 DB")
                        .description("Spring H2 DB API endpoints")
                        .version("0.0.1-SNAPSHOT")
                );
    }
}
