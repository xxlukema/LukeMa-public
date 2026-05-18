package com.learn.sync;


public class Deadlock
{
   public static void main(String[] args)
   {
      final Friend luke = new Friend("Luke");
      final Friend hong = new Friend("Hong");

      Thread lukeThread = new Thread(new Runnable()
      {
         public void run()
         {
            luke.bow(hong);
         }
      }, "Luke Thread");

      Thread hongThread = new Thread(new Runnable()
      {
         public void run()
         {
            hong.bow(luke);
         }
      }, "Hong Thread");

      lukeThread.start();
      hongThread.start();
   }

}


class Friend
{
   private final String name;

   public Friend(String name)
   {
      this.name = name;
   }

   public String getName()
   {
      return this.name;
   }

   public synchronized void bow(Friend friend)
   {
      System.out.println(getName() + " has bowed to " + friend.getName());
      friend.bowBack(this);
   }

   public synchronized void bowBack(Friend bower)
   {
      System.out.println(getName() + " has bowed back to " + bower.getName());
   }
}
