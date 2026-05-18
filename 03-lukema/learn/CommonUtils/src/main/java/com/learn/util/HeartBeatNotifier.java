package com.learn.util;


import java.util.TimerTask;

import com.learn.mail.Mailer;


public class HeartBeatNotifier
extends TimerTask
{
   private static final String SUBJECT = "Heart Beat";
   private static final String MESSAGE = "I am running.";

   public void run() 
   {
      Mailer.sendMail(SUBJECT, MESSAGE);
   }
}

