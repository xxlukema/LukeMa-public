package com.learn.b.swing.all.bollinger;


public class BollingerData
{
   private float middleBand;

   private float upperBand;

   public float getMiddleBand()
   {
      return middleBand;
   }

   public void setMiddleBand(float middleBand)
   {
      this.middleBand = middleBand;
   }

   public float getUpperBand()
   {
      return upperBand;
   }

   public void setUpperBand(float upperBand)
   {
      this.upperBand = upperBand;
   }

   public float getLowerBand()
   {
      return lowerBand;
   }

   public void setLowerBand(float lowerBand)
   {
      this.lowerBand = lowerBand;
   }

   private float lowerBand;

}
