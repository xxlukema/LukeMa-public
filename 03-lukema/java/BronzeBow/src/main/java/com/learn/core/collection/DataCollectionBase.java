package com.learn.core.collection;


import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.learn.common.domain.DomainDataBase;
import com.learn.common.util.ChartConstants;
import com.learn.core.raw.RawData;


abstract public class DataCollectionBase
{
   private Vector<RawData>     rawDataVector;

   private DomainDataBase      domainData;

   private final List<float[]> rawY      = new LinkedList<float[]>();

   private final List<int[]>   adjustedY = new LinkedList<int[]>();

   private int                 y0Position;

   private int                 height;

   private float               minY;

   private float               maxY;

   protected DataCollectionBase(Vector<RawData> rawDataVector, int y0Position)
   {
      init(rawDataVector, y0Position, false);
   }

   protected DataCollectionBase(Vector<RawData> rawDataVector, int y0Position, boolean isHistoryDataCollection)
   {
      init(rawDataVector, y0Position, isHistoryDataCollection);
   }

   private void init(Vector<RawData> rawDataVector, int y0Position, boolean isHistoryDataCollection)
   {
      this.rawDataVector = rawDataVector;
      this.y0Position = y0Position;

      setDomainData();

      domainData.setY0Position(y0Position);
      domainData.setKUnit(isKUint());

      if (isHistoryDataCollection)
      {
         height = ChartConstants.HistoryChartHeight;
      }
      else
      {
         height = ChartConstants.IndicatorChartHeight;
      }

      loadData();

      adjustY();
      domainData.setMax(maxY);
      domainData.setMin(minY);

      fillAdjustedY();
   }

   abstract protected void setDomainData();

   abstract protected void fillAdjustedY();

   abstract public void loadData();

   public boolean isKUint()
   {
      return false;
   }

   public void addRawY(float[] yy)
   {
      if (yy != null)
      {
         rawY.add(yy);
      }
   }

   private void adjustY()
   {
      if (rawY.size() == 0)
      {
         return;
      }

      minY = Integer.MAX_VALUE;
      maxY = Integer.MIN_VALUE;

      for (float[] yy : rawY)
      {
         for (float f : yy)
         {
            maxY = Math.max(f, maxY);
            minY = Math.min(f, minY);
         }
      }

      float fy = (float) (height / (maxY - minY));

      for (float[] yy : rawY)
      {
         int[] intY = new int[yy.length];

         for (int i = 0; i < yy.length; i++)
         {
            intY[i] = (int) (y0Position - (yy[i] - minY) * fy);
         }

         adjustedY.add(intY);
      }
   }

   public List<int[]> getAdjustedY()
   {
      return adjustedY;
   }

   protected void setHeight(int height)
   {
      this.height = height;
   }

   public float getMinY()
   {
      return minY;
   }

   public float getMaxY()
   {
      return maxY;
   }

   public List<float[]> getRawY()
   {
      return rawY;
   }

   protected void setDomainData(DomainDataBase domainData)
   {
      this.domainData = domainData;
   }

   @SuppressWarnings("unchecked")
   public <T> T getDomainData()
   {
      return (T) domainData;
   }

   public Vector<RawData> getRawDataVector()
   {
      return rawDataVector;
   }

}
