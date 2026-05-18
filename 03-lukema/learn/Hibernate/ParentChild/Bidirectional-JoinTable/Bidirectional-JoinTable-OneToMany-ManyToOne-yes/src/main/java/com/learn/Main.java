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
      addRecordChildInverseTrueWrongWay();
      addRecordChildInverseTrueRightWay();

      retrieveParents();
      retrieveChildren();
   }

   /**
    * This will show hui has no child because inverse="true" on child side.
    */
   public static void addRecordChildInverseTrueWrongWay()
      throws Exception
   {
      Parent hui = new Parent();
      hui.setName("Hui Liu");

      Child jenny = new Child();
      jenny.setName("Jenny Shi");

      jenny.setParent(hui);
      HibernateUtils.saveOrUpdate(jenny);

      // This will show hui has no child because inverse="true" on child side.
      retrieveParents();
   }

   /**
    * To be able to change child ownership, Parent cascade="delete-orphan" has to be removed.
    */
   public static void addRecordChildInverseTrueRightWay()
      throws Exception
   {
      Child candice = new Child();
      candice.setName("Candice Ma");

      Child natalie = new Child();
      natalie.setName("Natalie Ma");

      Parent luke = new Parent();
      luke.setName("Luke Ma");

      luke.getChildren().add(candice);
      luke.getChildren().add(natalie);
      HibernateUtils.saveOrUpdate(luke);

      Parent hong = new Parent();
      hong.setName("Hong Lin");

      // To be able to change child ownership, Parent cascade="delete-orphan" has to be removed.
      luke.getChildren().remove(natalie);
      hong.getChildren().add(natalie);

      HibernateUtils.saveOrUpdate(luke);
      HibernateUtils.saveOrUpdate(hong);
   }
}
