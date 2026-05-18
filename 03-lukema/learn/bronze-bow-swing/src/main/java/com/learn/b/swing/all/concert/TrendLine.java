package com.learn.b.swing.all.concert;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;


public class TrendLine
{
   private int           xA      = 0;

   private int           yA      = 0;

   private int           xZ      = 0;

   private int           yZ      = 0;

   private static Stroke stroke2 = new BasicStroke(2);

   public TrendLine(int xA, int yA, int xZ, int yZ)
   {
      this.xA = xA;
      this.yA = yA;
      this.xZ = xZ;
      this.yZ = yZ;
   }

   public void drawLine(Graphics g)
   {
      g.setColor(Color.BLACK);
      ((Graphics2D) g).setStroke(stroke2);
      g.drawLine(xA, yA, xZ, yZ);
   }

   public static void drawLine(Graphics g, int x0, int y0, int x1, int y1)
   {
      g.setColor(Color.BLACK);
      ((Graphics2D) g).setStroke(stroke2);
      g.drawLine(x0, y0, x1, y1);
   }
}
