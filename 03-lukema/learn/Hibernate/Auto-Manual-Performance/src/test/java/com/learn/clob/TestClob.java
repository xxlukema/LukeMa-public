package com.learn.clob;


import java.sql.SQLException;

import com.learn.clob.ObjectWithClobDriver;

import junit.framework.TestCase;


public class TestClob
   extends TestCase
{
   public void testAddRecord()
      throws Exception
   {
      for (int i = 0; i < 2; i++)
      {
         ObjectWithClobDriver.addRecord();
      }
   }

   public void testRetrieveData()
      throws SQLException
   {
      ObjectWithClobDriver.retrieveData();
   }

}
