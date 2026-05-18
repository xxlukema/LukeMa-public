package com.learn.exception;


import javax.ejb.ApplicationException;


@ApplicationException(rollback = true)
public class AppException
   extends Exception
{
   private static final long serialVersionUID = 1L;

   public AppException(String msg)
   {
      super(msg);
   }

   public AppException()
   {
      super();
   }

   public AppException(Throwable t)
   {
      super(t);
   }

   public AppException(String msg, Throwable t)
   {
      super(msg, t);
   }
}
