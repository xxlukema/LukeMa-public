package com.learn;


import com.learn.bean.Child;
import com.learn.bean.Parent;
import com.learn.hibernate.HibernateUtils;


public class Main
   extends ManyToManyMainBase
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
      Parent luke = new Parent();
      luke.setName("Luke Ma");

      Parent hong = new Parent();
      hong.setName("Hong Lin");

      Child candice = new Child();
      candice.setName("Candice Ma");

      candice.getParents().add(hong);
      candice.getParents().add(luke);

      HibernateUtils.saveOrUpdate(candice);

      Child natalie = new Child();
      natalie.setName("Natalie Ma");
      natalie.getParents().add(luke);

      HibernateUtils.saveOrUpdate(natalie);

      Parent hui = new Parent();
      hui.setName("Hui Liu");

      Child jenny = new Child();
      jenny.setName("Jenny Shi");

      jenny.getParents().add(hui);
      jenny.getParents().add(luke);

      HibernateUtils.saveOrUpdate(jenny);
   }
}
