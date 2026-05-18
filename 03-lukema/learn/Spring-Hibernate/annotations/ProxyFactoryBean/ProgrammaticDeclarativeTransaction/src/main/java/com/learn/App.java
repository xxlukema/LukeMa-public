package com.learn;


import com.learn.transaction.callback.CallbackTransactionClient;
import com.learn.transaction.declarative.DeclarativeTransactionClient;
import com.learn.transaction.noTransaction.NoTransactionClient;


public class App
{
   public static void main(String[] args)
      throws Exception
   {
      System.out.println("Hello World!");

      CallbackTransactionClient.main(null);

      DeclarativeTransactionClient.main(null);

      NoTransactionClient.main(null);
   }
}
