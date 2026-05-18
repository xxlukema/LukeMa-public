package com.learn.applet.painter;


import java.awt.Color;
import java.awt.Graphics;

import com.learn.common.domain.StdDomainData;
import com.learn.common.util.MbaUtils;


public class StdPainter
   extends PainterBase
{
   private StdDomainData stdDomainData;

   private float[]       stdRaw;

   public StdPainter(StdDomainData stdDomainData)
   {
      super(stdDomainData);

      this.stdDomainData = stdDomainData;

      setDomainData(stdDomainData);
      stdRaw = stdDomainData.getStdRaw();
   }

   public void descMe()
   {
      setDesc("STD: black");
   }

   public String getValueString(int index)
   {
      if (stdRaw != null)
      {
         return "STD=" + MbaUtils.DecimalFormat.format(stdRaw[index]);
      }
      else
      {
         return null;
      }
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
      int skipDays = getSkipDays();

      int[] std = MbaUtils.toArray(stdDomainData.getStd(), skipDays);

      drawLine(g, std, Color.BLACK, 2);
   }

   @Override
   protected int getSkipDays()
   {
      return stdRaw.length - getDays();
   }
}
