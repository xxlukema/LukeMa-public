package com.learn.common.domain;


import java.io.Serializable;

import com.learn.common.util.ChartConstants;


public class DomainDataBase
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private float             max;

   private float             min;

   private int               y0Position;

   private int               height           = ChartConstants.IndicatorChartHeight;

   private boolean           isKUnit          = false;

   public float getMax()
   {
      return max;
   }

   public int getHeight()
   {
      return height;
   }

   public void setHeight(int height)
   {
      this.height = height;
   }

   public void setMax(float max)
   {
      this.max = max;
   }

   public float getMin()
   {
      return min;
   }

   public void setMin(float min)
   {
      this.min = min;
   }

   public void setY0Position(int y0Position)
   {
      this.y0Position = y0Position;
   }

   public int getY0Position()
   {
      return y0Position;
   }

   public void setKUnit(boolean isKUnit)
   {
      this.isKUnit = isKUnit;
   }

   public boolean isKUnit()
   {
      return isKUnit;
   }

}
