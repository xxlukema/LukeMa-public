package com.learn.serverless.function;


import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;


/**
 * 1. POST is required, because GET does not support request body. Therefore, "Function" and "Consumer" all need POST methods.
 * 2. "Supplier" does not need body payload. Thereofore, "GET" is used for "Supplier". 
 */
@Configuration
@Log4j2
public class MySimpleFunction {

    /**
     * Function and Consumer use POST.
     * 
     * curl -H "Content-Type: text/plain" http://localhost:8080/reverse -d "Hello Function!"
     */
    @Bean
    public Function<String, String> reverse() {
        return (input) -> new StringBuilder(input).reverse().toString();
    }

    /**
     * Multi line function
     * 
     * Function and Consumer use POST.
     * 
     * curl -H "Content-Type: text/plain" http://localhost:8080/greeting3 -d "Luke Ma"
     */
    @Bean
    public Function<String, String> greeting3() {

        return (input) -> {
            log.info(() -> "Inside greeting3().");

            return String.format("Hello, %s!", input);
        };
    }

    /**
     * curl -H "Content-Type: application/json" http://localhost:8080/transform -d '{"id" : "1", "request": "This is request"}'
     * 
     * Function and Consumer use POST.
     * 
     * time curl -i -H "Content-Type: application/json" -X POST https://yn9jez4i94.execute-api.us-east-1.amazonaws.com/prod/transform -d '{"id" : "1", "request": "This is request"}'
     */
    @Bean
    public Function<MyRequest, MyResponse> transform() {

        return (request) -> {
            log.info("Inside greeting3 request: {}", () -> request);
            MyResponse response = new MyResponse();
            response.setId(100 + request.getId());
            String str = String.format("Received %s", request);
            response.setResponse(str);

            return response;
        };
    }

}


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
class MyRequest {
    private long id;
    private String request;
}


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
class MyResponse {
    private long id;
    private String response;
}
