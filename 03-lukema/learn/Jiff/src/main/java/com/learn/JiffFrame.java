package com.learn;


import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;


public class JiffFrame 
{
   private static JiffFrame instance = null;
   private JFrame jFrame = null;

   private final JScrollPane jsp1 = new JScrollPane();
   private final JScrollPane jsp2 = new JScrollPane();


   private JiffFrame()
   {
   }


   public static JiffFrame getInstance()
   {
      if(instance == null)
      {
         instance = new JiffFrame();
         instance.initInstance();
      }

      return instance;
   }


   public void setTitle(String title)
   {
      jFrame.setTitle(title);
   }


   private void initInstance()
   {
      jFrame = new JFrame("Jiff");
      jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      jFrame.addWindowListener(new WindowAdapter()
                          {
                             public void windowClosing(WindowEvent e) 
                             {
                                System.exit(0);
                             }
                          });

      //f.pack();
      //f.setSize(f.getMaximumSize());
      jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      jFrame.getContentPane().setLayout(new GridLayout(1, 2));
      jsp2.setVerticalScrollBar(jsp1.getVerticalScrollBar());
      jsp1.getVerticalScrollBar().setUnitIncrement(
                         jsp1.getVerticalScrollBar().getUnitIncrement() * 20);
      jsp2.setHorizontalScrollBar(jsp1.getHorizontalScrollBar());
      jFrame.getContentPane().add(jsp1);
      jFrame.getContentPane().add(jsp2);
   }


   public void display()
   {
      //f.pack();  // setSize to full window;
      jFrame.setVisible(true);
   }


   public JScrollPane getJScrollPane1()
   {
      return jsp1;
   }


   public JScrollPane getJScrollPane2()
   {
      return jsp2;
   }
}


