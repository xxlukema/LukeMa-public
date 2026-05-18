package gui.applet.test;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JApplet;


public class ChartJApplet
   extends JApplet
   implements MouseMotionListener
{
   private static final long serialVersionUID = 1L;

   private int               x;

   private int               y;

   private String            symbol           = null;

   public void init()
   {
      symbol = getParameter("symbol");

      if (symbol == null || symbol.trim().length() == 0)
      {
         symbol = "Lvs";
      }

      addMouseMotionListener(this);
   }

   public void paint(Graphics g)
   {
      g.setColor(Color.LIGHT_GRAY);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());

      g.setColor(Color.BLACK);
      g.drawString("symbol = " + symbol + ", x + " + x + ", y = " + y, 20, 100);
      g.drawString("symbol = " + symbol + ", x + " + x + ", y = " + y, 20, 400);
      g.drawString("symbol = " + symbol + ", x + " + x + ", y = " + y, 20, 800);
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
