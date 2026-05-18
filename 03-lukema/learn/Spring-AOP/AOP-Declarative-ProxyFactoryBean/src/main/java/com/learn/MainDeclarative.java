package com.learn;


import com.learn.bean.AfterBean;
import com.learn.util.SpringBeanFactory;


public class MainDeclarative
{
   public static void main(String[] args)
      throws Exception
   {
      // BeanOne
      AfterBean afterBean = SpringBeanFactory.getBean("afterBean");

      String name = afterBean.getName();
      System.out.println(name);

      afterBean.setName("New Name");
      name = afterBean.getName();
      System.out.println(name);
   }
}
