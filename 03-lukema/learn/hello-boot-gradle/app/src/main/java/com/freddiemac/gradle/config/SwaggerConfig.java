package com.freddiemac.gradle.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.freddiemac.gradle.rest.MyResource;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
@EnableAsync
public class SwaggerConfig {

    @Bean
    public Docket api() {
        // @formatter:off
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage(MyResource.class.getPackageName()))
                .paths(PathSelectors.any())
                .build()
                .forCodeGeneration(true);
        // @formatter:on
    }

    @Bean
    public UiConfiguration uiConfig() {
        // @formatter:off
        return UiConfigurationBuilder.builder()
                                        .deepLinking(true)
                                        .displayOperationId(false)
                                        .defaultModelExpandDepth(1)
                                        .defaultModelsExpandDepth(1)
                                        .defaultModelRendering(ModelRendering.EXAMPLE)
                                        .displayRequestDuration(false)
                                        .docExpansion(DocExpansion.NONE)
                                        .filter(false)
                                        .maxDisplayedTags(null)
                                        .operationsSorter(OperationsSorter.ALPHA)
                                        .showExtensions(true)
                                        .tagsSorter(TagsSorter.ALPHA)
                                        .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                                        .validatorUrl(null)
                                        .build();
        // @formatter:on
    }

}
