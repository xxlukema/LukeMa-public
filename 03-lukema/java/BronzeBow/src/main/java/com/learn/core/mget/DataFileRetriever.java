package com.learn.core.mget;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class DataFileRetriever
{
   private static String[] GET_LIST = { "get_index", "get_oil", "get_hot", "get_house", "get_coal", "get_tech" };

   private static Process  PROCESS  = null;

   public static void main(String[] args)
   {
      for (int i = 0; i < GET_LIST.length; i++)
      {
         String target = GET_LIST[i];

         MavenRunner mr = new MavenRunner(target);
         new Thread(mr).start();

         try
         {
            Thread.sleep(5000);
         }
         catch (Throwable t)
         {
            t.printStackTrace();
         }

         while (!MavenRunner.gotFiles())
         {
            try
            {
               Thread.sleep(1000);
            }
            catch (Throwable t)
            {
               t.printStackTrace();
            }
         }
      }

      System.exit(0);
   }

   public static Process getProcess()
   {
      return PROCESS;
   }

   public static void setProcess(Process p)
   {
      PROCESS = p;
   }
}


class MavenTimer
   implements Runnable
{
   private int     sleepTime     = 4000; // 1 second.

   private int     repeat        = 20;   // 1 minutes.

   private boolean isInterrupted = false;

   private String  target        = null;

   MavenTimer(String target)
   {
      this.target = target;
   }

   public boolean isInterrupted()
   {
      return isInterrupted;
   }

   public void run()
   {
      for (int i = 0; i < repeat; i++)
      {
         try
         {
            System.out.print(target + " " + i + " ");
            Thread.sleep(sleepTime);
         }
         catch (Throwable t)
         {
            isInterrupted = true;
            break;
         }
      }

      System.out.println("");

      if (isInterrupted)
      {
         System.out.println(target + " completed on time.\n\n");
      }
      else
      {
         if (DataFileRetriever.getProcess() != null)
         {
            DataFileRetriever.getProcess().destroy();
         }

         System.out.println(target + " time out. Retrying...");
         MavenRunner mr = new MavenRunner(target);
         new Thread(mr).start();
      }
   }
}


class MavenRunner
   implements Runnable
{
   private static boolean gotFiles = false;

   private String         target   = null;

   public static boolean gotFiles()
   {
      return gotFiles;
   }

   MavenRunner(String target)
   {
      this.target = target;
   }

   public void run()
   {
      gotFiles = false;

      MavenTimer mt = new MavenTimer(target);
      Thread timerThread = new Thread(mt);
      timerThread.start();
      System.out.println(target + " timer started.");

      try
      {
         String cmd = "maven.bat " + target;
         System.out.print(target + " process " + cmd + " starting... ");
         Process p = Runtime.getRuntime().exec(cmd);
         DataFileRetriever.setProcess(p);

         InputStream is = p.getInputStream();
         InputStream eis = p.getErrorStream();

         new Thread(new MavenStreamReader(is)).start();
         new Thread(new MavenStreamReader(eis)).start();

         System.out.println(target + " started.");

         p.waitFor();
         timerThread.interrupt();

         gotFiles = true;
      }
      catch (Throwable t)
      {
         t.printStackTrace();
      }
   }
}


class MavenStreamReader
   implements Runnable
{
   private InputStream inputStream = null;

   MavenStreamReader(InputStream is)
   {
      inputStream = is;
   }

   public void run()
   {
      InputStreamReader isr = null;
      BufferedReader br = null;

      try
      {
         isr = new InputStreamReader(inputStream);
         br = new BufferedReader(isr);

         String line = null;

         while ((line = br.readLine()) != null)
         {
            System.out.println(line);
         }
      }
      catch (Throwable t)
      {
         t.printStackTrace();
      }
      finally
      {
         if (br != null)
         {
            try
            {
               br.close();
            }
            catch (Throwable t)
            {
               t.printStackTrace();
            }
         }
         if (isr != null)
         {
            try
            {
               isr.close();
            }
            catch (Throwable t)
            {
               t.printStackTrace();
            }
         }
         if (inputStream != null)
         {
            try
            {
               inputStream.close();
            }
            catch (Throwable t)
            {
               t.printStackTrace();
            }
         }
      }
   }
}
