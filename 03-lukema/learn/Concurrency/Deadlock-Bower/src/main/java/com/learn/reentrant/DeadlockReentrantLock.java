package com.learn.reentrant;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DeadlockReentrantLock
{
   public static void main(String[] args)
   {
      final Friend luke = new Friend("Luke");
      final Friend hong = new Friend("Hong");

      Thread lukeThread = new MyThread("Luke Thread", luke, hong);
      Thread hongThread = new MyThread("Hong Thread", hong, luke);

      lukeThread.start();
      hongThread.start();
   }
}


class MyThread
   extends Thread
{
   private Friend host;

   private Friend guest;

   public MyThread(String name, Friend host, Friend guest)
   {
      super(name);

      this.host = host;
      this.guest = guest;
   }

   @Override
   public void run()
   {
      host.bow(guest);
   }
}


class Friend
{
   private final String name;

   private final Lock   LOCK  = new ReentrantLock();

   protected final Lock LOCK2 = new ReentrantLock();

   public Friend(String name)
   {
      this.name = name;
   }

   public String getName()
   {
      return this.name;
   }

   public void bow(Friend friend)
   {
      LOCK.lock();

      try
      {
         System.out.println(getName() + " has bowed to " + friend.getName());
         friend.bowBack(this);
      }
      finally
      {
         LOCK.unlock();
      }
   }

   public void bowBack(Friend bower)
   {
      LOCK.lock();

      try
      {
         System.out.println(getName() + " has bowed back to " + bower.getName());
      }
      finally
      {
         LOCK.unlock();
      }
   }
}
