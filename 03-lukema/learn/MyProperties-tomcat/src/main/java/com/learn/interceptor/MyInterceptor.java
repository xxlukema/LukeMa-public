package com.learn.interceptor;


import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MyInterceptor {

    private static final Logger LOG = LogManager.getLogger();

    @AroundInvoke
    public Object around(InvocationContext ctx)
        throws Exception {
        
        LOG.info("around() called");
        
        return ctx.proceed();
    }
}
