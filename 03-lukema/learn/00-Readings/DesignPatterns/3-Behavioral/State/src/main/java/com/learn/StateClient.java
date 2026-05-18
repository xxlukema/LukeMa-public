package com.learn;


public class StateClient
{
   public static void main(String[] args)
   {
      StateContext ctx = new StateContext();
      ctx.acceptApplication();
      ctx.requestPermission();
      ctx.grantPermission();
      System.out.println(ctx.getStatus());
   }
}
