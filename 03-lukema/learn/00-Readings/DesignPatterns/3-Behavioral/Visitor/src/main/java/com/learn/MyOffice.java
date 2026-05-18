package com.learn;


public class MyOffice
   implements Place
{
   private final String PUBLIC_DATA  = "PUBLIC_DATA " + MyOffice.class.getSimpleName();

   private final String PRIVATE_DATA = "PRIVATE_DATA " + MyOffice.class.getSimpleName();

   public String getPUBLIC_DATA()
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
