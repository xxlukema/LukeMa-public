package com.learn.applet.painter;


import java.awt.Color;
import java.awt.Graphics;

import com.learn.common.domain.AroonDomainData;
import com.learn.common.util.MbaUtils;


public class AroonPainter
   extends PainterBase
{
   private AroonDomainData aroonDomainData;

   private float[]         aroonUpRaw;

   private float[]         aroonDownRaw;

   public AroonPainter(AroonDomainData aroonDomainData)
   {
      super(aroonDomainData);

      this.aroonDomainData = aroonDomainData;

      setDomainData(aroonDomainData);
      aroonUpRaw = aroonDomainData.getAroonUpRaw();
      aroonDownRaw = aroonDomainData.getAroonDownRaw();
   }

   public void descMe()
   {
      setDesc("Aroon: Up-blue, Down-red");
   }

   public String getValueString(int index)
   {
      if (aroonUpRaw != null)
      {
         return "Up=" + MbaUtils.LongFormat.format(aroonUpRaw[index]) + " Down=" + MbaUtils.LongFormat.format(aroonDownRaw[index]);
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
      drawAroon(g);
      drawDesc(g);
   }

   private void drawAroon(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] aroonUp = MbaUtils.toArray(aroonDomainData.getAroonUp(), skipDays);
      int[] aroonDown = MbaUtils.toArray(aroonDomainData.getAroonDown(), skipDays);

      drawLine(g, aroonUp, Color.BLUE, 2);
      drawLine(g, aroonDown, Color.RED, 2);
   }

   @Override
   protected int getSkipDays()
   {
      return aroonUpRaw.length - getDays();
   }
}
