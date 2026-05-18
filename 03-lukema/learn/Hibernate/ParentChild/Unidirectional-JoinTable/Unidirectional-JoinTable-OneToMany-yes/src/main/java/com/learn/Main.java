package com.learn;


import com.learn.bean.Child;
import com.learn.bean.Parent;
import com.learn.hibernate.HibernateUtils;


public class Main
   extends OneToManySetMainBase
{
   public static void main(String[] args)
      throws Exception
   {
      addRecord();

      // One directional. Candice can not Luke.
      retrieveChildren();

      // One directional. Luke sees Candice and Natalie.
      retrieveParents();
   }

   public static void addRecord()
      throws Exception
   {
      // Luke Ma
      Child candice = new Child();
      candice.setName("Candice Ma");

      Child natalie = new Child();
      natalie.setName("Natalie Ma");

      Parent luke = new Parent();
      luke.setName("Luke Ma");

      luke.getChildren().add(candice);
      luke.getChildren().add(natalie);

      HibernateUtils.saveOrUpdate(luke);
   }
}
