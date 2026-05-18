package com.learn.transaction;


import javax.ejb.ApplicationException;


@ApplicationException(rollback = false)
public class AnotherAppException
   extends NullPointerException
{
   private static final long serialVersionUID = 1L;

   public AnotherAppException()
   {
      super();
   }

   public AnotherAppException(String message)
   {
      super(message);
   }

}
