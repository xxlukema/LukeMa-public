package com.learn.boot.config;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.learn.rest.resource.PingNoSecurityController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
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
@Profile({ "dev", "test" })
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {

    private static final String ApiKeyName = "JWT";

    private boolean isPingEnabled = true;

    @Bean
    public Docket api() {

        // @formatter:off
        /*
        List<Response> globalResponses = Arrays.asList(
                new Response("400", "Bad Request", false, null, null, null, null),
                new Response("401", "Unauthorized", false, null, null, null, null),
                new Response("403", "Forbidden", false, null, null, null, null),
                new Response("404", "Not Found", false, null, null, null, null),
                new Response("406", "Not Acceptable", false, null, null, null, null),
                new Response("500", "Internal Error", false, null, null, null, null)
        );
        */

        List<SecurityContext> securityContext = new ArrayList<>();
        List<SecurityScheme> apiKeys = new ArrayList<>();

        if (isPingEnabled) {
            securityContext.add(pingSecurityContext());
            apiKeys.add(apiKey());
        }

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .securityContexts(securityContext)
                .securitySchemes(apiKeys)
                // .globalResponses(HttpMethod.GET, globalResponses)
                // .globalResponses(HttpMethod.POST, globalResponses)
                // .globalResponses(HttpMethod.DELETE, globalResponses)
                // .globalResponses(HttpMethod.PUT, globalResponses)
                .select()
                .apis(RequestHandlerSelectors.basePackage(PingNoSecurityController.class.getPackageName()))
                .paths(PathSelectors.any())
                .build();
        // @formatter:on
    }

    private ApiKey apiKey() {
        return new ApiKey(ApiKeyName, "AUTHORIZATION", "header");
    }

    private SecurityContext pingSecurityContext() {
        // @formatter:off
        return SecurityContext.builder()
                               .securityReferences(pingSecurityAuthRef())
                               // .operationSelector(operationContext -> operationContext.requestMappingPattern().matches("/**"))
                               .build();
        // @formatter:off
    }

    private List<SecurityReference> pingSecurityAuthRef() {
        AuthorizationScope [] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("Read", "Write");
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference(ApiKeyName, authorizationScopes));
        return securityReferences;
    }

    private ApiInfo apiInfo() {
      // @formatter:off
      return new ApiInfoBuilder()
          .title("HelloSpringBootService API")
          .description("Hello SpringBoot Service")
          .version("1.0.test")
          .contact(new Contact("Luke Ma", "http://learn.com", "xx.luke.ma@gmail.com"))
          .build();
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

    /**
     * https://stackoverflow.com/questions/70036953/springboot-2-6-0-spring-fox-3-failed-to-start-bean-documentationpluginsboot
     *
     * Magic fix of:
     *
     * Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException:
     * Cannot invoke "org.springframework.web.servlet.mvc.condition.PatternsRequestCondition.getPatterns()" because "this.condition" is null
     */
    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(
            WebEndpointsSupplier webEndpointsSupplier,
            ServletEndpointsSupplier servletEndpointsSupplier,
            ControllerEndpointsSupplier controllerEndpointsSupplier,
            EndpointMediaTypes endpointMediaTypes,
            CorsEndpointProperties corsProperties,
            WebEndpointProperties webEndpointProperties,
            Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(),
                new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }

    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled()
                && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }
}
