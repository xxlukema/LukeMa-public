package com.learn.common.domain;


public class CmoDomainData
   extends DomainDataBase
{
   private static final long serialVersionUID = 1L;

   private float[]           cmo05Raw;

   private float[]           cmo10Raw;

   private int[]             cmo05;

   private int[]             cmo10;

   public float[] getCmo05Raw()
   {
      return cmo05Raw;
   }

   public void setCmo05Raw(float[] cmo05Raw)
   {
      this.cmo05Raw = cmo05Raw;
   }

   public float[] getCmo10Raw()
   {
      return cmo10Raw;
   }

   public void setCmo10Raw(float[] cmo10Raw)
   {
      this.cmo10Raw = cmo10Raw;
   }

   public int[] getCmo05()
   {
      return cmo05;
   }

   public void setCmo05(int[] cmo05)
   {
      this.cmo05 = cmo05;
   }

   public int[] getCmo10()
   {
      return cmo10;
   }

   public void setCmo10(int[] cmo10)
   {
      this.cmo10 = cmo10;
   }

}
