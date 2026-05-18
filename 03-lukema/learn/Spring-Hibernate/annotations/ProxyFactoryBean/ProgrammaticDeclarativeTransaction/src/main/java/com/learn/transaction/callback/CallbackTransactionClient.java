package com.learn.transaction.callback;


import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.learn.ClientBase;
import com.learn.util.SpringBeanFactory;


public class CallbackTransactionClient
   extends ClientBase
{
   public static void main(String[] args)
      throws Exception
   {
      addRecord();

      testUpdateTransaction();

      queryRecords();
   }

   /////////////////////////////////////////////////////////////////
   /// Callback Transaction Management (Using TransactionTemplate)
   /////////////////////////////////////////////////////////////////
   public static void testUpdateTransaction()
      throws Exception
   {
      TransactionTemplate transactionTemplate = SpringBeanFactory.getBean("transactionTemplate");

      TransactionCallback transactionCallback = new TransactionCallbackRollback();
      transactionTemplate.execute(transactionCallback);

      transactionCallback = new TransactionCallbackCommit();
      transactionTemplate.execute(transactionCallback);
   }
}
