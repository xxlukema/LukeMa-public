package com.learn.b.swing.all.concert;


import java.awt.Color;
import java.awt.Graphics;

import com.learn.b.swing.all.raw.RawDataUtils;
import com.learn.b.swing.all.accum_dist_line.AccumDistLineData;
import com.learn.b.swing.all.accum_dist_line.AccumDistLineDataUtils;


public class AccumDistLineDataCollection
   extends DataCollectionBase
{
   private float[] accumDistLine;

   public AccumDistLineDataCollection(int y_L, int y_H)
   {
      super(y_L, y_H);
   }

   public void descMe()
   {
      setDesc("AccumDistLine: black");
   }

   public boolean isKUint()
   {
      return true;
   }

   public String getValueString(int index)
   {
      if (accumDistLine != null)
      {
         return "AccumDistLine=" + roundFloat2String(accumDistLine[index]);
      }
      else
      {
         return null;
      }
   }

   public void addData()
   {
      int len = RawDataUtils.getCHART_DAYS();

      accumDistLine = new float[len];

      for (int i = 0; i < len; i++)
      {
         int k = RawDataUtils.getData().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         accumDistLine[i] = ((AccumDistLineData) AccumDistLineDataUtils.getData().get(k)).getAccumDistLine() / 1000;
      }

      addY(accumDistLine);
   }

   public void paint(Graphics g)
   {
      drawGrid(g);
      drawCross(g);
      drawAccumDistLine(g);
      drawDesc(g);
   }

   private void drawAccumDistLine(Graphics g)
   {
      int[] accumDistLine = (int[]) getADJUSTED_Y().get(0);

      drawLine(g, accumDistLine, Color.BLACK, 2);
   }
}
