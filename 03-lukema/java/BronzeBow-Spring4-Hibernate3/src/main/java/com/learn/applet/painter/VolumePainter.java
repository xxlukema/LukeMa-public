package com.learn.applet.painter;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.learn.common.domain.VolumeDomainData;
import com.learn.common.util.MbaUtils;


public class VolumePainter
   extends PainterBase
{
   private VolumeDomainData volumeDomainData;

   private float[]          volumeRaw;

   private float[]          closeRaw = getChartJApplet().getHistoryDomainData().getCloseRaw();

   public VolumePainter(VolumeDomainData volumeDomainData)
   {
      super(volumeDomainData);

      this.volumeDomainData = volumeDomainData;

      setDomainData(volumeDomainData);
      volumeRaw = volumeDomainData.getVolumeRaw();
   }

   public void descMe()
   {
      setDesc("Volume");
   }

   public String getValueString(int index)
   {
      if (volumeRaw != null)
      {
         return "Volume=" + MbaUtils.LongFormat.format(volumeRaw[index]) + "K";
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
      drawVolume(g);
      drawDesc(g);
   }

   private void drawVolume(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] volume = MbaUtils.toArray(volumeDomainData.getVolume(), skipDays);

      int x[] = getDateArray();

      Stroke s = new BasicStroke(2);
      ((Graphics2D) g).setStroke(s);

      Color c = Color.BLUE;
      g.setColor(c);

      float lastClose = 0;
      float todayClose = 0;

      for (int i = 0; i < volume.length; i++)
      {
         if (closeRaw != null)
         {
            todayClose = closeRaw[i];

            if (i == 0)
            {
               lastClose = todayClose;
            }

            if (todayClose < lastClose)
            {
               c = Color.RED;
            }
            else
            {
               c = Color.BLUE;
            }

            g.setColor(c);
         }

         g.drawLine(x[i], getY0Position(), x[i], volume[i]);
         lastClose = todayClose;
      }
   }

   @Override
   protected int getSkipDays()
   {
      return volumeRaw.length - getDays();
   }
}
