package com.learn;


public class LukeMa
   implements ToBeVisited
{
   private final MyHome   HOME   = new MyHome();

   private final MyOffice OFFICE = new MyOffice();

   public MyHome getHOME()
   {
      return HOME;
   }

   public MyOffice getOFFICE()
   {
      return OFFICE;
   }

   public Place accept(Visitor visitor)
   {
      if(visitor instanceof MyFriend)
      {
         return getHOME();
      }
      else
      {
         return getOFFICE();
      }
   }
}
