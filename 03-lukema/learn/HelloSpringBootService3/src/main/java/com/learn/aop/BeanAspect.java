package com.learn.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Configurable
@Aspect
@Component
public class BeanAspect {
    public void beforeEcho(JoinPoint joinPoint) {
        log.debug(() -> "Before *** *** Before *** *** Before *** ** \t" + getPointcutInfo(joinPoint));
        joinPointInfo(joinPoint);
    }

    public void afterReturningEcho(JoinPoint joinPoint, String retVal) {
        log.debug(() -> "After returning*** *** After returning*** * \t" + getPointcutInfo(joinPoint));
        joinPointInfo(joinPoint);
        log.debug(() -> "   retVal: " + retVal);
    }

    public void afterThrowingEcho(JoinPoint joinPoint, Exception ex) {
        log.debug(() -> "After throwing*** *** After throwing*** *** \t" + getPointcutInfo(joinPoint));
        joinPointInfo(joinPoint);
        log.debug(() -> "   ex.getMessage(): " + ex.getMessage());
    }

    public void afterEcho(JoinPoint joinPoint) {
        log.debug(() -> "After *** *** After *** *** After *** *** * \t" + getPointcutInfo(joinPoint));
    }

    public Object aroundEcho(ProceedingJoinPoint proceedingJoinPoint)
        throws Throwable {
        log.debug(() -> "Around A*** *** Around A*** *** Around A*** \t" + getPointcutInfo(proceedingJoinPoint));

        Object object = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        joinPointInfo(proceedingJoinPoint);

        log.debug(() -> "Around Z*** *** Around Z*** *** Around Z*** \t" + getPointcutInfo(proceedingJoinPoint));

        return object;
    }

    private String getPointcutInfo(JoinPoint joinPoint) {
        String target = joinPoint.getTarget().getClass().getName();
        String signatureName = joinPoint.getSignature().getName();

        return target + "." + signatureName + "()";
    }

    private void joinPointInfo(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.debug(() -> "          arg: " + arg);
        }

        String signatureName = joinPoint.getSignature().getName();
        log.debug(() -> "   signatureName: " + signatureName);

        String target = joinPoint.getTarget().getClass().getName();
        log.debug(() -> "          target: " + target);

        String thiz = joinPoint.getThis().getClass().getName();
        log.debug(() -> "            this: " + thiz);

        String kind = joinPoint.getKind();
        log.debug(() -> "            kind: " + kind);

        /*
        String longString = joinPoint.toLongString();
        log.debug(() -> "      longString: " + longString);
        
        String shortString = joinPoint.toShortString();
        log.debug(() -> "     shortString: " + shortString);
        */
    }
}
