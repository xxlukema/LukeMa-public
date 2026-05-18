package com.learn.b.swing.all.concert;


import java.awt.Color;
import java.awt.Graphics;

import com.learn.b.swing.all.raw.RawDataUtils;
import com.learn.b.swing.all.aroon.AroonData;
import com.learn.b.swing.all.aroon.AroonDataUtils;


public class AroonDataCollection
   extends DataCollectionBase
{
   private static float[] aroonUp   = null;

   private static float[] aroonDown = null;

   public AroonDataCollection(int y_L, int y_H)
   {
      super(y_L, y_H);
   }

   public void descMe()
   {
      setDesc("Aroon: Up-blue, Down-red");
   }

   public String getValueString(int index)
   {
      if (aroonUp != null)
      {
         return "Up=" + roundFloat2String(aroonUp[index]) + " Down=" + roundFloat2String(aroonDown[index]);
      }
      else
      {
         return null;
      }
   }

   public void addData()
   {
      int len = RawDataUtils.getCHART_DAYS();

      aroonUp = new float[len];
      aroonDown = new float[len];

      for (int i = 0; i < len; i++)
      {
         int k = RawDataUtils.getData().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         aroonUp[i] = ((AroonData) AroonDataUtils.getData().get(k)).getAroonUp();
         aroonDown[i] = ((AroonData) AroonDataUtils.getData().get(k)).getAroonDown();
      }

      addY(aroonUp);
      addY(aroonDown);
   }

   public void paint(Graphics g)
   {
      drawGrid(g);
      drawCross(g);
      drawAroon(g);
      drawDesc(g);
   }

   private void drawAroon(Graphics g)
   {
      int[] aroonUp = (int[]) getADJUSTED_Y().get(0);
      int[] aroonDown = (int[]) getADJUSTED_Y().get(1);

      drawLine(g, aroonUp, Color.BLUE, 2);
      drawLine(g, aroonDown, Color.RED, 2);
   }
}
