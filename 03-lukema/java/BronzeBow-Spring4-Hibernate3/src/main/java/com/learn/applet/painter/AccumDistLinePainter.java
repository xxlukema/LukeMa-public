package com.learn.applet.painter;


import java.awt.Color;
import java.awt.Graphics;

import com.learn.common.domain.AccumDistLineDomainData;
import com.learn.common.util.MbaUtils;


public class AccumDistLinePainter
   extends PainterBase
{
   private AccumDistLineDomainData accumDistLineDomainData;

   private float[]                 accumDistLineRaw;

   public AccumDistLinePainter(AccumDistLineDomainData accumDistLineDomainData)
   {
      super(accumDistLineDomainData);

      this.accumDistLineDomainData = accumDistLineDomainData;

      setDomainData(accumDistLineDomainData);
      accumDistLineRaw = accumDistLineDomainData.getAccumDistLineRaw();
   }

   public void descMe()
   {
      setDesc("AccumDistLine: black");
   }

   public String getValueString(int index)
   {
      if (accumDistLineRaw != null)
      {
         return "AccumDistLine=" + MbaUtils.LongFormat.format(accumDistLineRaw[index]) + "K";
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
      drawAccumDistLine(g);
      drawDesc(g);
   }

   private void drawAccumDistLine(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] accumDistLine = MbaUtils.toArray(accumDistLineDomainData.getAccumDistLine(), skipDays);

      drawLine(g, accumDistLine, Color.BLACK, 2);
   }

   @Override
   protected int getSkipDays()
   {
      return accumDistLineRaw.length - getDays();
   }
}
