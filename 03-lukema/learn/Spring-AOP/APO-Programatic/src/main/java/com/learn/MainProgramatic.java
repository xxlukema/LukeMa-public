package com.learn;


import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import com.learn.advice.SimpleAfterAdvice;
import com.learn.bean.AfterBean;


public class MainProgramatic
{
   public static void main(String[] args)
   {
      AfterBean target = new AfterBean();
      target.setName("Hello World!");
      
      AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
      pointcut.setExpression("execution(* com..AfterBean.*Name*(..))");

      SimpleAfterAdvice simpleAfterAdvice = new SimpleAfterAdvice();
      Advisor advisor = new DefaultPointcutAdvisor(pointcut, simpleAfterAdvice);

      ProxyFactory proxyFactory = new ProxyFactory();
      proxyFactory.setTarget(target);
      proxyFactory.addAdvisor(advisor);

      AfterBean beanOneProxy = (AfterBean) proxyFactory.getProxy();
      String name = beanOneProxy.getName();
      System.out.println(name);
      
      beanOneProxy.setName("New Name");
      name = beanOneProxy.getName();
      System.out.println(name);
   }
}
