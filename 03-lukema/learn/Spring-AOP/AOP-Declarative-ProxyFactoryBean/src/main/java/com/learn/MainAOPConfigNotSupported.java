package com.learn;


import com.learn.bean.BeanTwo;
import com.learn.util.SpringBeanFactory;


public class MainAOPConfigNotSupported
{
   public static void main(String[] args)
   {
      // BeanTwo
      BeanTwo beanTwo = SpringBeanFactory.getBean("beanTwo");

      String name = beanTwo.getName();
      System.out.println(name);

      beanTwo.setName("New Name");
   }
}
