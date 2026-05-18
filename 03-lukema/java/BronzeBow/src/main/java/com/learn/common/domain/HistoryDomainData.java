package com.learn.common.domain;


import com.learn.common.util.ChartConstants;


public class HistoryDomainData
   extends DomainDataBase
{
   private static final long serialVersionUID = 1L;

   private String[]          date;

   private float[]           openRaw;

   private float[]           highRaw;

   private float[]           lowRaw;

   private float[]           closeRaw;

   private int[]             open;

   private int[]             high;

   private int[]             low;

   private int[]             close;

   private int[]             bollingerUpper;

   private int[]             bollingerLower;

   private int[]             ema05;

   private int[]             ema10;

   private int[]             ema15;

   private int[]             ema25;

   private int[]             ema50;

   private int[]             highTBP;

   private int[]             lowTBP;

   private int[]             highTarget;

   private int[]             lowTarget;

   private int[]             highStop;

   private int[]             lowStop;

   private int[]             tomorrowHighTarget;

   private int[]             tomorrowLowTarget;

   private int[]             tomorrowHighStop;

   private int[]             tomorrowLowStop;

   private String            tomorrowRangeString;

   public int getHeight()
   {
      return ChartConstants.HistoryChartHeight;
   }

   public String[] getDate()
   {
      return date;
   }

   public void setDate(String[] date)
   {
      this.date = date;
   }

   public float[] getOpenRaw()
   {
      return openRaw;
   }

   public void setOpenRaw(float[] openRaw)
   {
      this.openRaw = openRaw;
   }

   public float[] getHighRaw()
   {
      return highRaw;
   }

   public void setHighRaw(float[] highRaw)
   {
      this.highRaw = highRaw;
   }

   public float[] getLowRaw()
   {
      return lowRaw;
   }

   public void setLowRaw(float[] lowRaw)
   {
      this.lowRaw = lowRaw;
   }

   public float[] getCloseRaw()
   {
      return closeRaw;
   }

   public void setCloseRaw(float[] closeRaw)
   {
      this.closeRaw = closeRaw;
   }

   public int[] getOpen()
   {
      return open;
   }

   public void setOpen(int[] open)
   {
      this.open = open;
   }

   public int[] getHigh()
   {
      return high;
   }

   public void setHigh(int[] high)
   {
      this.high = high;
   }

   public int[] getLow()
   {
      return low;
   }

   public void setLow(int[] low)
   {
      this.low = low;
   }

   public int[] getClose()
   {
      return close;
   }

   public void setClose(int[] close)
   {
      this.close = close;
   }

   public int[] getBollingerUpper()
   {
      return bollingerUpper;
   }

   public void setBollingerUpper(int[] bollingerUpper)
   {
      this.bollingerUpper = bollingerUpper;
   }

   public int[] getBollingerLower()
   {
      return bollingerLower;
   }

   public void setBollingerLower(int[] bollingerLower)
   {
      this.bollingerLower = bollingerLower;
   }

   public int[] getEma05()
   {
      return ema05;
   }

   public void setEma05(int[] ema05)
   {
      this.ema05 = ema05;
   }

   public int[] getEma10()
   {
      return ema10;
   }

   public void setEma10(int[] ema10)
   {
      this.ema10 = ema10;
   }

   public int[] getEma15()
   {
      return ema15;
   }

   public void setEma15(int[] ema15)
   {
      this.ema15 = ema15;
   }

   public int[] getEma25()
   {
      return ema25;
   }

   public void setEma25(int[] ema25)
   {
      this.ema25 = ema25;
   }

   public int[] getEma50()
   {
      return ema50;
   }

   public void setEma50(int[] ema50)
   {
      this.ema50 = ema50;
   }

   public int[] getHighTBP()
   {
      return highTBP;
   }

   public void setHighTBP(int[] highTBP)
   {
      this.highTBP = highTBP;
   }

   public int[] getLowTBP()
   {
      return lowTBP;
   }

   public void setLowTBP(int[] lowTBP)
   {
      this.lowTBP = lowTBP;
   }

   public int[] getHighTarget()
   {
      return highTarget;
   }

   public void setHighTarget(int[] highTarget)
   {
      this.highTarget = highTarget;
   }

   public int[] getLowTarget()
   {
      return lowTarget;
   }

   public void setLowTarget(int[] lowTarget)
   {
      this.lowTarget = lowTarget;
   }

   public int[] getHighStop()
   {
      return highStop;
   }

   public void setHighStop(int[] highStop)
   {
      this.highStop = highStop;
   }

   public int[] getLowStop()
   {
      return lowStop;
   }

   public void setLowStop(int[] lowStop)
   {
      this.lowStop = lowStop;
   }

   public int[] getTomorrowHighTarget()
   {
      return tomorrowHighTarget;
   }

   public void setTomorrowHighTarget(int[] tomorrowHighTarget)
   {
      this.tomorrowHighTarget = tomorrowHighTarget;
   }

   public int[] getTomorrowLowTarget()
   {
      return tomorrowLowTarget;
   }

   public void setTomorrowLowTarget(int[] tomorrowLowTarget)
   {
      this.tomorrowLowTarget = tomorrowLowTarget;
   }

   public int[] getTomorrowHighStop()
   {
      return tomorrowHighStop;
   }

   public void setTomorrowHighStop(int[] tomorrowHighStop)
   {
      this.tomorrowHighStop = tomorrowHighStop;
   }

   public int[] getTomorrowLowStop()
   {
      return tomorrowLowStop;
   }

   public void setTomorrowLowStop(int[] tomorrowLowStop)
   {
      this.tomorrowLowStop = tomorrowLowStop;
   }

   public String getTomorrowRangeString()
   {
      return tomorrowRangeString;
   }

   public void setTomorrowRangeString(String tomorrowRangeString)
   {
      this.tomorrowRangeString = tomorrowRangeString;
   }

}
