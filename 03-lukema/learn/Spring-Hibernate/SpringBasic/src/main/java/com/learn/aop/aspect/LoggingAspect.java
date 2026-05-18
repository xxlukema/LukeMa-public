package com.learn.aop.aspect;


import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;


@Configurable
@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOG = Logger.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.learn.aop.bean.CustomerService.add*(..))")
    public void logBusinessMethods() {
        LOG.debug("logBusinessMethods() is running! *********************** !");
    }

    @Pointcut(value = "@annotation(com.learn.aop.aspect.annotation.MyAspect)", argNames = "myAspect")
    protected void logAnnotatedMethods() {
        LOG.debug("logAnnotatedMethods() is running! *********************** !");
    }

    @Before("logBusinessMethods()")
    public void logBefore(JoinPoint joinPoint) {

        LOG.debug("logBefore() is running!");
        LOG.debug("hijacked : " + joinPoint.getSignature().getName());
        LOG.debug("******");
    }

    @After("execution(* com.learn.aop.bean.CustomerService.addCustomer(..))")
    public void logAfter(JoinPoint joinPoint) {

        LOG.debug("logAfter() is running!");
        LOG.debug("hijacked : " + joinPoint.getSignature().getName());
        LOG.debug("******");

    }

    @AfterReturning(pointcut = "execution(* com.learn.aop..*.addCustomerReturnValue(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {

        LOG.debug("logAfterReturning() is running!");
        LOG.debug("hijacked : " + joinPoint.getSignature().getName());
        LOG.debug("Method returned value is : " + result);
        LOG.debug("******");

    }

    @AfterThrowing(pointcut = "logBusinessMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {

        LOG.debug("logAfterThrowing() is running!");
        LOG.debug("hijacked : " + joinPoint.getSignature().getName());
        LOG.debug("Exception : " + error);
        LOG.debug("******");

    }

    @Around("logBusinessMethods()")
    public void logAround(ProceedingJoinPoint joinPoint)
        throws Throwable {

        LOG.debug("logAround() is running!");
        LOG.debug("hijacked method : " + joinPoint.getSignature().getName());
        LOG.debug("hijacked arguments : " + Arrays.toString(joinPoint.getArgs()));

        LOG.debug("Around before is running!");
        joinPoint.proceed(); //continue on the intercepted method
        LOG.debug("Around after is running!");

        LOG.debug("******");

    }

    @Around("logAnnotatedMethods()")
    public void logAroundAnnotation(ProceedingJoinPoint joinPoint)
        throws Throwable {

        LOG.debug("######");

        LOG.debug("logAroundAnnotation() is running!");
        LOG.debug("hijacked method : " + joinPoint.getSignature().getName());
        LOG.debug("hijacked arguments : " + Arrays.toString(joinPoint.getArgs()));

        LOG.debug("Around before is running!");
        Object result = joinPoint.proceed(); //continue on the intercepted method
        LOG.debug("Method returned value is : " + result);
        LOG.debug("Around after is running!");

        LOG.debug("######");

    }
}
