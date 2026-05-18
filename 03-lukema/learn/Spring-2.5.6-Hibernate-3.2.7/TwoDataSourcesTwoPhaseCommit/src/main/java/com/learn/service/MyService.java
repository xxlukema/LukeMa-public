package com.learn.service;


import com.learn.bean.mysql.MySQLObject;
import com.learn.bean.oracle.OracleObject;


public interface MyService
{
   public void list()
      throws Exception;

   /**
    * Throws AppException to cause automatic roll back. See interceptor config.
    */
   public void saveObjects(OracleObject oracleObject, MySQLObject mySQLObject)
      throws Exception;

   /**
    * Throws Exception will NOT cause automatic roll back. See interceptor config.
    */
   public void saveObjectsRollback(OracleObject oracleObject, MySQLObject mySQLObject)
      throws Exception;

}
