package com.learn.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;


/**
 * Advice: Indicate the action to take either before or after the method execution.
 * Pointcut: Indicate which method should be intercept, by method name or regular expression pattern. 
 * Advisor: Group 'Advice' and 'Pointcut' into a single unit, and pass it to a proxy factory object.
 * 
 * Pointcut: the expression used to define when a call to a method should be intercepted.
 * A pointcut expression starts with a pointcut designator (PCD).
 * (1) execution: The primary Spring PCD is execution.
 *     @Pointcut("execution(public String org.baeldung.dao.FooDao.findById(Long))")
 *     @Pointcut("execution(* org.baeldung.dao.FooDao.*(..))")
 * (2) within: Another way to achieve the same result from the previous section is by using the within PCD.
 *     @Pointcut("within(org.baeldung.dao.FooDao)")
 *     @Pointcut("within(org.baeldung..*)")
 * (2a) @within
 *      @Pointcut("@within(org.springframework.stereotype.Repository)")
 *      It is equivalent to
 *      @Pointcut("within(@org.springframework.stereotype.Repository *)") 
 * (3) this
 *     @Pointcut("this(org.baeldung.dao.FooDao)")
 * (4) target
 *     @Pointcut("target(org.baeldung.dao.BarDao)")
 * (4a) @target
 *      @Pointcut("@target(org.springframework.stereotype.Repository)")
 * (5) args
 *     @Pointcut("execution(* *..find*(Long,..))")
 * (6) @annotation    
 * 
 * Advice: What do you want to do with the Pointcut?
 * 
 * (1) @Before
 * (2) @Around
 * (3) @After
 * (4) @AfterReturning
 * (5) @AfterThrowing
 * 
 * Aspect: A combination of defining when you want to intercept a method call (Pointcut) and what to do (Advice) is an Aspect.
 * 
 * @Component is also OK instead of @Configuration
 */
@Log4j2
@Configuration
@Aspect
public class LogAspect {

    @AfterThrowing(pointcut = "execution(* com.learn.rest..*.*(..))", throwing = "ex")
    public void logAfterThrowingAllMethods1(JoinPoint jp, Exception ex)
        throws Throwable {
        log.error("aop - rest Exception thrown from: {}. Exception message: {}", jp.getSignature(), ex.getMessage(), ex);
    }

    @Around(value = "execution(public * com.learn.rest..*.*(..))")
    public Object pointcutSignature1Advise(ProceedingJoinPoint pjp)
        throws Throwable {
        log.debug("aop - rest: {}", pjp.getSignature());
        return pjp.proceed();
    }

    @Around(value = "execution(public * com.learn.util.*.*(..))")
    public Object pointcutSignature2Advise(ProceedingJoinPoint pjp)
        throws Throwable {
        log.debug("aop - util: {}", pjp.getSignature());
        return pjp.proceed();
    }

    @Around(value = "execution(public * com.learn.service.*.*(..))")
    public Object pointcutSignature3Advise(ProceedingJoinPoint pjp)
        throws Throwable {
        log.debug("aop - service: {}", pjp.getSignature());
        return pjp.proceed();
    }

    @Around(value = "execution(public * com.learn..impl.*.*(..))")
    public Object pointcutSignature4Advise(ProceedingJoinPoint pjp)
        throws Throwable {
        log.debug("aop - impl: {}", pjp.getSignature());
        return pjp.proceed();
    }

    /*
    @Around(value = "execution(public * org.usac.li.microservice.nvca.ext..*(..))")
    public Object pointcutSignature4Advise(ProceedingJoinPoint pjp)
        throws Throwable {
        log.debug("aop - ext: {}", pjp.getSignature());
        return pjp.proceed();
    }
    */

}
