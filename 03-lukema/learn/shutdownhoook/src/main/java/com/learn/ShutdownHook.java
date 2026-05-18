package com.learn;


import java.io.*;


public class ShutdownHook
extends Thread
{
   public void run() 
   {
      System.out.println("Hello. I am ShutdownHook.");
   }
}



