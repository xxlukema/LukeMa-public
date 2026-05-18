package com.learn.b.swing.all.std;


public class StandardDeviationData
{
   private float std;

   private float closeSMA;

   private float stdEMA;

   private int   timePeriod;

   public float getStd()
   {
      return std;
   }

   public void setStd(float std)
   {
      this.std = std;
   }

   public float getCloseSMA()
   {
      return closeSMA;
   }

   public void setCloseSMA(float closeSMA)
   {
      this.closeSMA = closeSMA;
   }

   public float getStdEMA()
   {
      return stdEMA;
   }

   public void setStdEMA(float stdEMA)
   {
      this.stdEMA = stdEMA;
   }

   public int getTimePeriod()
   {
      return timePeriod;
   }

   public void setTimePeriod(int timePeriod)
   {
      this.timePeriod = timePeriod;
   }

   public float getEMAofSTD()
   {
      return stdEMA;
   }
}
