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

      // One directional. Candice sees Luke.
      retrieveChildren();

      // One directional. Luke can not see Candice.
      retrieveParents();
   }

   public static void addRecord()
      throws Exception
   {
      // Luke Ma
      Parent luke = new Parent();
      luke.setName("Luke Ma");

      Child candice = new Child();
      candice.setName("Candice Ma");
      candice.setParent(luke);

      HibernateUtils.saveOrUpdate(candice);

      Child natalie = new Child();
      natalie.setName("Natalie Ma");
      natalie.setParent(luke);

      HibernateUtils.saveOrUpdate(natalie);
   }
}
