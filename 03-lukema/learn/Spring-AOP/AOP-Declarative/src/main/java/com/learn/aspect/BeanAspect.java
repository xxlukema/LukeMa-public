package com.learn.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;


public class BeanAspect
{
   public void beforeEcho(JoinPoint joinPoint)
   {
      System.out.println("Before *** *** Before *** *** Before *** ** \t" + getPointcutInfo(joinPoint));
      joinPointInfo(joinPoint);
   }

   public void afterReturningEcho(JoinPoint joinPoint, String retVal)
   {
      System.out.println("After returning*** *** After returning*** * \t" + getPointcutInfo(joinPoint));
      joinPointInfo(joinPoint);
      System.out.println("   retVal: " + retVal);
   }

   public void afterThrowingEcho(JoinPoint joinPoint, Exception ex)
   {
      System.out.println("After throwing*** *** After throwing*** *** \t" + getPointcutInfo(joinPoint));
      joinPointInfo(joinPoint);
      System.out.println("   ex.getMessage(): " + ex.getMessage());
   }

   public void afterEcho(JoinPoint joinPoint)
   {
      System.out.println("After *** *** After *** *** After *** *** * \t" + getPointcutInfo(joinPoint));
   }

   public Object aroundEcho(ProceedingJoinPoint proceedingJoinPoint)
      throws Throwable
   {
      System.out.println("Around A*** *** Around A*** *** Around A*** \t" + getPointcutInfo(proceedingJoinPoint));

      Object object = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
      joinPointInfo(proceedingJoinPoint);

      System.out.println("Around Z*** *** Around Z*** *** Around Z*** \t" + getPointcutInfo(proceedingJoinPoint));

      return object;
   }

   private String getPointcutInfo(JoinPoint joinPoint)
   {
      String target = joinPoint.getTarget().getClass().getName();
      String signatureName = joinPoint.getSignature().getName();

      return target + "." + signatureName + "()";
   }

   private void joinPointInfo(JoinPoint joinPoint)
   {
      Object[] args = joinPoint.getArgs();
      for (Object arg : args)
      {
         System.out.println("          arg: " + arg);
      }

      String signatureName = joinPoint.getSignature().getName();
      System.out.println("   signatureName: " + signatureName);

      String target = joinPoint.getTarget().getClass().getName();
      System.out.println("          target: " + target);

      String thiz = joinPoint.getThis().getClass().getName();
      System.out.println("            this: " + thiz);

      String kind = joinPoint.getKind();
      System.out.println("            kind: " + kind);
      
      /*
      String longString = joinPoint.toLongString();
      System.out.println("      longString: " + longString);
      
      String shortString = joinPoint.toShortString();
      System.out.println("     shortString: " + shortString);
      */
   }
}
