package com.learn;


import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;


public class MyConnectionListener
   implements ConnectionEventListener
{
   public void connectionClosed(ConnectionEvent event)
   {
      System.out.println("Connection closed: " + event.toString());
   }

   public void connectionErrorOccurred(ConnectionEvent event)
   {
      System.out.println("Connection error: " + event.getSQLException());
   }
}
