package com.learn;


public class LockedObject
{
   private static final LockedObject instance = new LockedObject();

   public static LockedObject getInstance()
   {
      return instance;
   }

   private LockedObject()
   {
   }
}
