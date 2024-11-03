package com.varc.brewnetapp.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "BrewNet API Specification",
                description = "BrewNet APIs",
                version = "v1"
        )
)
@Configuration
public class SpecConfiguration {
}
