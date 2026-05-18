package com.learn.service;


import com.learn.bean.mysql.MySQLObject;
import com.learn.bean.oracle.OracleObject;
import com.learn.mysql.dao.WidgetDAO;
import com.learn.oracle.dao.PersonDAO;


public interface MyService
{
   /**
    * Setter is invoked in SpringBeanConfig.xml
    */
   public void setPersonDAO(PersonDAO personDAO);

   /**
    * Setter is invoked in SpringBeanConfig.xml
    */
   public void setWidgetDAO(WidgetDAO widgetDAO);
   
   /**
    * Throws AppException to cause automatic roll back. See interceptor config.
    */
   public void updatePersonRollback()
      throws Exception;

   /**
    * Throws Exception will NOT cause automatic roll back. See interceptor config.
    */
   public void updatePersonCommit()
      throws Exception;
      
   /**
    * Throws AppException to cause automatic roll back. See interceptor config.
    */
   public void saveObjects(OracleObject oracleObject, MySQLObject mySQLObject)
      throws Exception;

}
