package com.companies.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition (
    info = @Info(
            title = "Companies",
            version = "1.0.0",
            description = "Microservice of companies"
    )
)
public class OpenApiConfig {
}
