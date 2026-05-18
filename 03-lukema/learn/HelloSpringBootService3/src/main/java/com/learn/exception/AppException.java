package com.learn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_MODIFIED, reason="It can be a database related problem.") 
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
