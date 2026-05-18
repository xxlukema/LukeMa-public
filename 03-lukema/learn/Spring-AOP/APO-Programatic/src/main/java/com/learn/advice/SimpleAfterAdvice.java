package com.learn.advice;


import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;


public class SimpleAfterAdvice
   implements AfterReturningAdvice
{
   public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
      throws Throwable
   {
      System.out.println("************************* SimpleAfterAdvice.afterReturning: " + method.getName());
   }
}
