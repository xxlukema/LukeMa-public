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

      // Since cascade is delete-orphan. you do not have to delete child/children prior to deleting parent.
      // deleteChildren();
      deleteParents();
      
      retrieveParents();
      retrieveChildren();
   }

   public static void addRecord()
      throws Exception
   {
      Parent luke = new Parent();
      luke.setName("Luke Ma");

      // Since cascade is all, parent objects do have have to be saved explicitly. 
      // HibernateUtils.saveOrUpdate(luke);

      Child candice = new Child();
      candice.setName("Candice Ma");
      candice.setParent(luke);

      // Since cascade is all, saving child objects will also save parent objects. 
      HibernateUtils.saveOrUpdate(candice);
   }

}
