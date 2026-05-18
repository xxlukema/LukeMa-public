package com.learn.common.domain;


public class DmiDomainData
   extends DomainDataBase
{
   private static final long serialVersionUID = 1L;

   private float[]           diPlusRaw;

   private float[]           diMinusRaw;

   private float[]           adxRaw;

   private int[]             diPlus;

   private int[]             diMinus;

   private int[]             adx;

   public float[] getDiPlusRaw()
   {
      return diPlusRaw;
   }

   public void setDiPlusRaw(float[] diPlusRaw)
   {
      this.diPlusRaw = diPlusRaw;
   }

   public float[] getDiMinusRaw()
   {
      return diMinusRaw;
   }

   public void setDiMinusRaw(float[] diMinusRaw)
   {
      this.diMinusRaw = diMinusRaw;
   }

   public float[] getAdxRaw()
   {
      return adxRaw;
   }

   public void setAdxRaw(float[] adxRaw)
   {
      this.adxRaw = adxRaw;
   }

   public int[] getDiPlus()
   {
      return diPlus;
   }

   public void setDiPlus(int[] diPlus)
   {
      this.diPlus = diPlus;
   }

   public int[] getDiMinus()
   {
      return diMinus;
   }

   public void setDiMinus(int[] diMinus)
   {
      this.diMinus = diMinus;
   }

   public int[] getAdx()
   {
      return adx;
   }

   public void setAdx(int[] adx)
   {
      this.adx = adx;
   }

}
