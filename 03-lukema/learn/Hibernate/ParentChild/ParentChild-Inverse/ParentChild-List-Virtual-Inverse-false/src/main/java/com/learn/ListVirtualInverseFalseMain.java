package com.learn;


import com.learn.bean.Child;
import com.learn.bean.Parent;
import com.learn.hibernate.HibernateUtils;


public class ListVirtualInverseFalseMain
   extends OneToMantListMainBase
{
   public static void main(String[] args)
      throws Exception
   {
      addRetriveRecord();
   }

   public static void addRetriveRecord()
      throws Exception
   {
      // Luke Ma
      Parent luke = new Parent();
      luke.setName("Luke Ma");

      Child candice = new Child();
      candice.setName("Candice Ma");

      luke.getChildren().add(candice);

      Child natalie = new Child();
      natalie.setName("Natalie Ma");

      luke.getChildren().add(natalie);

      HibernateUtils.saveOrUpdate(luke);

      // Hui Liu
      Parent hui = new Parent();
      hui.setName("Hui Liu");

      Child jenny = new Child();
      jenny.setName("Jenny Shi");

      hui.getChildren().add(jenny);

      HibernateUtils.saveOrUpdate(hui);

      // Retrieve. hui has jenny
      retrieveParents();

      luke.getChildren().add(jenny);

      HibernateUtils.saveOrUpdate(luke);

      // Retrive. hui has no child/children. luke has jenny.
      retrieveParents();
   }

}
