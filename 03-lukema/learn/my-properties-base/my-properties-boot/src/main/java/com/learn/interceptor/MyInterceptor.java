package com.learn.interceptor;


import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class MyInterceptor {

    @AroundInvoke
    public Object around(InvocationContext ctx)
        throws Exception {

        log.info(() -> "around() called");

        return ctx.proceed();
    }
}
