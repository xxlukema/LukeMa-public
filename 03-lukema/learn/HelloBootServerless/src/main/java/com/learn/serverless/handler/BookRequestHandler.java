package com.learn.serverless.handler;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;


/**
 * Not in use.
 * 
 * implements RequestHandler<I, R>
 */
@Log4j2
public class BookRequestHandler
    implements RequestHandler<String, MyResponse> {

    @Override
    public MyResponse handleRequest(String input, Context context) {
        log.info("input: {}", () -> input);

        String str = String.format("Hello, %s", input);

        return new MyResponse(101, str);
    }

}


@Data
@AllArgsConstructor
class MyResponse {
    private Integer id;
    private String message;
}
