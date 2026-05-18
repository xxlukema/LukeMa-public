package com.learn.common.domain;


public class AroonDomainData
   extends DomainDataBase
{
   private static final long serialVersionUID = 1L;

   private float[]           aroonUpRaw;

   private float[]           aroonDownRaw;

   private int[]             aroonUp;

   private int[]             aroonDown;

   public float[] getAroonUpRaw()
   {
      return aroonUpRaw;
   }

   public void setAroonUpRaw(float[] aroonUpRaw)
   {
      this.aroonUpRaw = aroonUpRaw;
   }

   public float[] getAroonDownRaw()
   {
      return aroonDownRaw;
   }

   public void setAroonDownRaw(float[] aroonDownRaw)
   {
      this.aroonDownRaw = aroonDownRaw;
   }

   public int[] getAroonUp()
   {
      return aroonUp;
   }

   public void setAroonUp(int[] aroonUp)
   {
      this.aroonUp = aroonUp;
   }

   public int[] getAroonDown()
   {
      return aroonDown;
   }

   public void setAroonDown(int[] aroonDown)
   {
      this.aroonDown = aroonDown;
   }

}
