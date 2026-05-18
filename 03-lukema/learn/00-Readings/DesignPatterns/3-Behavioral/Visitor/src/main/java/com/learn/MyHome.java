package com.learn;


public class MyHome
   implements Place
{
   private final String PUBLIC_DATA  = "PUBLIC_DATA of " + MyHome.class.getSimpleName();

   private final String PRIVATE_DATA = "PRIVATE_DATA of " + MyHome.class.getSimpleName();

   protected String getPUBLIC_DATA()
   {
      return PUBLIC_DATA;
   }

   protected String getPRIVATE_DATA()
   {
      return PRIVATE_DATA;
   }
   
   public String getVisitable()
   {
      return getPUBLIC_DATA();
   }
}
