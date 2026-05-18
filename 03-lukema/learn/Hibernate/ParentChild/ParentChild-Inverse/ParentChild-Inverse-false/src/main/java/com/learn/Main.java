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
       * By setting inverse="false", candice.setParent(luke) lose the race
       * to hui.getChildren().add(candice). 
       * But becuase there is no race in this test, candice.setParent(luke) will 
       * win because there is no race.
       */
      candice.setParent(luke);

      HibernateUtils.saveOrUpdate(candice);

      Parent hui = new Parent();
      hui.setName("Hui Liu");

      Child jenny = new Child();
      jenny.setName("Jenny Shi");
      
      /**
       * By setting inverse="false", jenny.setParent(hui) will lose the race.
       * luke.getChildren().add(jenny) will win the race.
       */
      jenny.setParent(hui);

      HibernateUtils.saveOrUpdate(jenny);

      /**
       * By setting inverse="false", jenny.setParent(hui) will be ignored (lose the race). 
       * luke.getChildren().add(jenny) will be used (win the race).
       * The result of this test will be luke has two children and hui has none.
       */
      luke.getChildren().add(jenny);  // This will prevail.
      HibernateUtils.saveOrUpdate(luke);
   }
}
