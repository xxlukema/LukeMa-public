package com.learn.common.domain;


public class OnBalanceVolumeDomainData
   extends DomainDataBase
{
   private static final long serialVersionUID = 1L;

   private float[]           obvRaw;

   private float[]           pvtRaw;

   private int[]             obv;

   private int[]             pvt;

   public float[] getObvRaw()
   {
      return obvRaw;
   }

   public void setObvRaw(float[] obvRaw)
   {
      this.obvRaw = obvRaw;
   }

   public float[] getPvtRaw()
   {
      return pvtRaw;
   }

   public void setPvtRaw(float[] pvtRaw)
   {
      this.pvtRaw = pvtRaw;
   }

   public int[] getObv()
   {
      return obv;
   }

   public void setObv(int[] obv)
   {
      this.obv = obv;
   }

   public int[] getPvt()
   {
      return pvt;
   }

   public void setPvt(int[] pvt)
   {
      this.pvt = pvt;
   }

}
