package com.hughesntc.co.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import springfox.documentation.spring.web.json.Json;


/**
 * https://stackoverflow.com/questions/53155161/how-to-use-google-gson-instead-of-the-default-jackson-of-the-spring-in-swagger
 * 1. https://github.com/swagger-api/swagger-core/issues/1826
 * 2. https://github.com/springfox/springfox/issues/1608
 * 3. https://stackoverflow.com/questions/30219946/springfoxswagger2-does-not-work-with-gsonhttpmessageconverterconfig
 */
@Configuration
public class GsonHttpMessageConverterConfig {

    @Bean
    public GsonHttpMessageConverter gsonHttpMessageConerter() {
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(gson());
        return gsonHttpMessageConverter;
    }

    private Gson gson() {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Json.class, new SpringfoxJsonToGsonAdapter());
        return gsonBuilder.create();
    }

}
