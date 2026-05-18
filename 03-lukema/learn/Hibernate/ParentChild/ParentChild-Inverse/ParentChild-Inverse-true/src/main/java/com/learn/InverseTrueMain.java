package com.learn;


import com.learn.bean.Child;
import com.learn.bean.Parent;
import com.learn.hibernate.HibernateUtils;


public class InverseTrueMain
   extends OneToManySetMainBase
{
   public static void main(String[] args)
      throws Exception
   {
      addRecord();

      retrieveParents();
   }

   public static void addRecord()
      throws Exception
   {
      Parent luke = new Parent();
      luke.setName("Luke Ma");

      Child candice = new Child();
      candice.setName("Candice Ma");
      
      /**
       * By setting inverse="true", Child maintains relationship. 
       * candice.setParent(luke) will win because there is no race.
       */
      candice.setParent(luke);

      HibernateUtils.saveOrUpdate(candice);

      Parent hui = new Parent();
      hui.setName("Hui Liu");

      Child jenny = new Child();
      jenny.setName("Jenny Shi");
      
      /**
       * By setting inverse="true", jenny.setParent(hui) will win the race.
       * luke.getChildren().add(jenny) will lose the race.
       */
      jenny.setParent(hui);

      HibernateUtils.saveOrUpdate(jenny);

      /**
       * By setting inverse="true", jenny.setParent(hui) will be used (win the race). 
       * luke.getChildren().add(jenny) will be ignored (lose the race).
       * The result of this test will be luke has one child and hui has one child.
       */
      luke.getChildren().add(jenny);  // This will be ignored.
      HibernateUtils.saveOrUpdate(luke);
   }

}
