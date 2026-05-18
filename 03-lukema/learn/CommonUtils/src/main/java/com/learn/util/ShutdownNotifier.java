package com.learn.util;


import com.learn.mail.Mailer;


public class ShutdownNotifier
extends Thread
{
   public void run() 
   {
      String subject = "Application Shutdown";
      String message = "Application Shutdown.";

      Mailer.sendPage(subject, message);
      Mailer.cancel();
   }
}



