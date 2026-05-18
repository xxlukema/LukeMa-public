package com.learn.b.swing.all.common;


public class Setting
{
   // After data is expended, discard first STABLE_DAY days data as
   // data of first STABLE_DAY days are used to initialize expended 
   // data and the expended data during the initialization days are
   // unstable.
   public static final int STABLE_DAY = 30;

   // delta X for concert (a small increment of x)
   // public static final int DX = 10;
   // FF: Zoom Far. MM: Zoom Middle. CC: Zoom Close.
   public static final int FF_DX      = 4;

   public static final int MM_DX      = 9;

   public static final int CC_DX      = 18;

   // public static final int HALF_CYCLE = 14;
   public static final int HALF_CYCLE = 5;
}
