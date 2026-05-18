package com.learn.persistence.bean;


public class AccessHotList
   extends AccessBase
{
   private static final long serialVersionUID = 1L;

   private long              accessCounter;

   public void setAccessCounter(long accessCounter)
   {
      this.accessCounter = accessCounter;
   }

   public long getAccessCounter()
   {
      return accessCounter;
   }
}
