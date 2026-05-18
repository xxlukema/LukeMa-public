package com.learn.b.swing.all.concert;


import java.awt.Color;
import java.awt.Graphics;

import com.learn.b.swing.all.raw.RawDataUtils;
import com.learn.b.swing.all.std.StandardDeviationDataUtils;


public class STDDataCollection
   extends DataCollectionBase
{
   private static float[] std = null;

   public STDDataCollection(int y_L, int y_H)
   {
      super(y_L, y_H);
   }

   public void descMe()
   {
      setDesc("STD: black");
   }

   public String getValueString(int index)
   {
      if (std != null)
      {
         return "STD=" + roundFloat2String(std[index]);
      }
      else
      {
         return null;
      }
   }

   public void addData()
   {
      int len = RawDataUtils.getCHART_DAYS();

      std = new float[len];

      for (int i = 0; i < len; i++)
      {
         int k = RawDataUtils.getData().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         std[i] = StandardDeviationDataUtils.getData().get(k).getStd();
      }

      addY(std);
   }

   public void paint(Graphics g)
   {
      drawGrid(g);
      drawCross(g);
      drawSTD(g);
      drawDesc(g);
   }

   private void drawSTD(Graphics g)
   {
      int[] std = (int[]) getADJUSTED_Y().get(0);

      drawLine(g, std, Color.BLACK, 2);
   }
}
