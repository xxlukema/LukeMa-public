package com.learn.b.swing.all.concert;


import java.awt.Color;
import java.awt.Graphics;

import com.learn.b.swing.all.raw.RawDataUtils;
import com.learn.b.swing.all.dmi.DMIDataUtils;


public class DMIDataCollection
   extends DataCollectionBase
{
   private static float[] diPlus  = null;

   private static float[] diMinus = null;

   private static float[] adx     = null;

   public DMIDataCollection(int y_L, int y_H)
   {
      super(y_L, y_H);
   }

   public void descMe()
   {
      setDesc("DMI: DI+ blue, DI- red, ADX green. ADX > 25: Strong trend. ADX < 20: No trend. ADX going down: Trend is ending.");
   }

   public String getValueString(int index)
   {
      if (diPlus != null)
      {
         return "DI+=" + (int) diPlus[index] + " DI-=" + (int) diMinus[index] + " ADX=" + (int) adx[index];
      }
      else
      {
         return null;
      }
   }

   public void addData()
   {
      int len = RawDataUtils.getCHART_DAYS();

      diPlus = new float[len];
      diMinus = new float[len];
      adx = new float[len];

      for (int i = 0; i < len; i++)
      {
         int k = RawDataUtils.getData().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         diPlus[i] = DMIDataUtils.getData().get(k).getPlusDI14();
         diMinus[i] = DMIDataUtils.getData().get(k).getMinusDI14();
         adx[i] = DMIDataUtils.getData().get(k).getAdx();
      }

      addY(diPlus);
      addY(diMinus);
      addY(adx);
   }

   public void paint(Graphics g)
   {
      drawGrid(g);
      drawCross(g);
      drawDMI(g);
      drawDesc(g);
   }

   private void drawDMI(Graphics g)
   {
      int[] diPlus = (int[]) getADJUSTED_Y().get(0);
      int[] diMinus = (int[]) getADJUSTED_Y().get(1);
      int[] adx = (int[]) getADJUSTED_Y().get(2);

      drawLine(g, diPlus, Color.BLUE, 2);
      drawLine(g, diMinus, Color.RED, 2);
      drawLine(g, adx, Color.GREEN, 2);
   }
}
