package com.learn.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.log4j.Log4j2;


/**
 * Advice – Indicate the action to take either before or after the method execution.
 * Pointcut – Indicate which method should be intercept, by method name or regular expression pattern. 
 * Advisor – Group 'Advice' and 'Pointcut' into a single unit, and pass it to a proxy factory object.
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
 * Aspect: A combination of defining when you want to intercept a method call (Pointcut) and what to do (Advice) is called an Aspect.
 * 
 * @Component is also OK instead of @Configuration
 */
@Log4j2
// @Configuration
// @Aspect
public class LogAspectNotInUse {

    /**
     * How to make 'within' work?
     * @param pjp
     * @throws Throwable
     */
    @Around(value = "within(com.learn.rest.resource..*)")
    public Object pointcutSignature2Advise(ProceedingJoinPoint pjp)
        throws Throwable {
        log.debug("=== 2a === Called {}", pjp.getSignature());
        return pjp.proceed();
    }

    /**
     * @Before is shaded by @Around.
     * @After is not shaded by @Around.
     */
    @After(value = "execution(public * com.learn.rest.resource.*Controller.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.debug("Controller Before/After method: {}", joinPoint.getSignature());
    }

    /*
    @After(value = "execution(* com.javainuse.service.EmployeeService.*(..)) and args(name,empId)")
    public void afterAdvice(JoinPoint joinPoint, String name, String empId) {
        log.debug("After method:" + joinPoint.getSignature());
        log.debug("Successfully created Employee with name - " + name + " and id - " + empId);
    }
    */

    @Pointcut(value = "execution(public * com.learn.rest.resource.*.*(..))")
    public void pointcutSignature1() {
    }

    @Around("pointcutSignature1()")
    public Object pointcutSignature1Advise(ProceedingJoinPoint pjp)
        throws Throwable {
        log.debug("=========== 1a ========== @Pointcut: getSignature-{}", pjp.getSignature());
        return pjp.proceed();
    }

    /*
    @Pointcut("execution(* com.learn.rest.resource.GreetingRestController.*(..))")
    public void dataLayerExecution2(JoinPoint joinPoint) {
        log.debug("=========== 2 ========== @Pointcut: getTarget-{} getClass-{} getSignature-{}", joinPoint.getTarget(), joinPoint.getClass(), joinPoint.getSignature());
    }
    
    @Around("dataLayerExecution2()")
    public void measureMethodExecutionTime2(ProceedingJoinPoint pjp)
        throws Throwable {
        log.debug("=========== 2a ========== @Pointcut: getTarget-{} getClass-{} getSignature-{}", pjp.getTarget(), pjp.getClass(), pjp.getSignature());
    }
    */

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint)
        throws Throwable {

        log.info("=========== 3 ========== Inside logExecutionTime(). method: {}", () -> joinPoint.getSignature());

        return joinPoint.proceed();
    }

}
