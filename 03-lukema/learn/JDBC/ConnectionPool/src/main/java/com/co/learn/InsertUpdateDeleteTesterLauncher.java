package com.co.learn;


import java.io.*;
import java.util.*;
import java.sql.*;

import org.apache.log4j.Logger;

import com.microsoft.jdbc.sqlserver.SQLServerDriver;


public class InsertUpdateDeleteTesterLauncher
{
   public static void launchANewThread()
   {
      while (true)
      {
         if (ThreadMonitor.getBlockedCounter() < InsertUpdateDeleteTester.TOTAL_ALLOWED_BLOCKED_THREADS)
         {
            Runnable r = new Runnable()
            {
               public void run() 
               {
                  InsertUpdateDeleteTester iud = new InsertUpdateDeleteTester();
                  iud.testUpdate();
               }
            };

            new Thread(r).start();

            break;
         }
         else
         {
            try
            {
               System.out.print("=");
               Thread.sleep(1000);
            }
            catch (Throwable t)
            {
            }
         }
      }
   }
}

