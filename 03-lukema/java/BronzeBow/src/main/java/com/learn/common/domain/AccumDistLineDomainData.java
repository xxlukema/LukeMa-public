package com.learn.common.domain;


public class AccumDistLineDomainData
   extends DomainDataBase
{
   private static final long serialVersionUID = 1L;

   private float[]           accumDistLineRaw;

   private int[]             accumDistLine;

   public float[] getAccumDistLineRaw()
   {
      return accumDistLineRaw;
   }

   public void setAccumDistLineRaw(float[] accumDistLineRaw)
   {
      this.accumDistLineRaw = accumDistLineRaw;
   }

   public int[] getAccumDistLine()
   {
      return accumDistLine;
   }

   public void setAccumDistLine(int[] accumDistLine)
   {
      this.accumDistLine = accumDistLine;
   }
}
