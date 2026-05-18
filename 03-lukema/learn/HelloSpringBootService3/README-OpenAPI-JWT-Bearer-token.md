# OpenAPI + JWT + Bearer token

    Content-Type: application/json
    Authorization: Bearer {{OPENAI_API_KEY}}

## `pom.xml`

    <!-- OpenAPI -->
    <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.8.6</version>
    </dependency>

## `OpenApiConfig.java`

    package com.learn.boot.config;

    import io.swagger.v3.oas.annotations.OpenAPIDefinition;
    import io.swagger.v3.oas.annotations.info.Info;
    import io.swagger.v3.oas.annotations.security.SecurityRequirement;
    import io.swagger.v3.oas.annotations.security.SecurityScheme;
    import io.swagger.v3.oas.annotations.security.SecuritySchemes;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    
    @Configuration
    @OpenAPIDefinition(info = @Info(title = "My API"), 
                       security = @SecurityRequirement(name = "bearerAuth"))
    @SecuritySchemes({
        @SecurityScheme(name = "bearerAuth", 
                        type = SecuritySchemeType.HTTP, 
                        scheme = "bearer", 
                        bearerFormat = "JWT")
    })
    public class OpenAPIConfig {
    }
