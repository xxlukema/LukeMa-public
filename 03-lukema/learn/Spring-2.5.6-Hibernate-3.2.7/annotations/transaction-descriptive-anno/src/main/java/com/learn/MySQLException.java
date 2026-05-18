package com.learn;


public class MySQLException extends Exception
{
   public MySQLException(String msg)
   {
      super(msg);
   }

   public MySQLException()
   {
      super();
   }

   public MySQLException(Throwable t)
   {
      super(t);
   }

   public MySQLException(String msg, Throwable t)
   {
      super(msg, t);
   }
}

