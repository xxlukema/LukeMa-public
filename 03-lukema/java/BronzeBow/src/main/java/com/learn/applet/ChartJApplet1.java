package com.learn.applet;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JApplet;

import com.learn.common.util.DataStreamer;


public class ChartJApplet1
   extends JApplet
   implements MouseMotionListener
{
   private static final long serialVersionUID = 1L;

   private int               x;

   private int               y;

   private String            symbol           = null;

   String                    sym              = null;

   public void init()
   {
      symbol = getParameter("symbol");

      if (symbol == null || symbol.trim().length() == 0)
      {
         symbol = "Lvs";
      }

      addMouseMotionListener(this);

      String data = "ACED0005740003686F76";

      try
      {
         String str = DataStreamer.hexStringDeserializeToObject(data);

         sym = str;
      }
      catch (Throwable e)
      {
         sym = e.getMessage();

         throw new RuntimeException(e);
      }
   }

   public void paint(Graphics g)
   {
      g.setColor(Color.LIGHT_GRAY);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());

      g.setColor(Color.BLACK);
      g.drawString(sym + ": symbol = " + symbol + ", x + " + x + ", y = " + y, 20, 100);
   }

   public void mouseDragged(MouseEvent e)
   {
   }

   public void mouseMoved(MouseEvent e)
   {
      x = e.getX();
      y = e.getY();

      repaint();
   }

}
