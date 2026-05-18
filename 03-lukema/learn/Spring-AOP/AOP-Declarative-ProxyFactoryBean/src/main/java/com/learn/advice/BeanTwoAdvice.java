package com.learn.advice;

import org.aspectj.lang.ProceedingJoinPoint;

public class BeanTwoAdvice
{
   public Object echoOne(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
   {
      System.out.println("Echo One ... ... One ... ... One ... ... One ... ...");
      
      return proceedingJoinPoint.proceed();
   }
   
   public void echoTwo()
   {
      System.out.println("Echo Two ... ... Two ... ... Two ... ... Two ... ...");
   }

}
