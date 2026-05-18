package com.learn;


import java.awt.Color;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class JiffDriver
{
   private final List<String> v1   = new LinkedList<String>();

   private final List<String> v2   = new LinkedList<String>();

   private int                c1   = 1;

   private int                c2   = 1;

   private JScrollPane        jsp1 = null;

   private JScrollPane        jsp2 = null;

   private JPanel             jp1  = new JPanel();

   private JPanel             jp2  = new JPanel();

   public static void main(String[] args)
   {
      JiffDriver jd = new JiffDriver();

      if (args.length != 2)
      {
         System.out.println("\n\tUsage: jiff fileName1 fileName2\n");
         System.exit(1);
      }

      jd.go(args[0], args[1]);
   }

   private void readFile2Vector(String file, List<String> v)
   {
      BufferedReader br = null;

      try
      {
         br = new BufferedReader(new FileReader(file));
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
         System.exit(1);
      }

      String line = null;
      try
      {
         while ((line = br.readLine()) != null)
         {
            // Trim tail
            line = "1" + line;
            line = line.trim();
            line = line.substring(1);

            v.add(line);
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
         System.exit(1);
      }

      try
      {
         br.close();
      }
      catch (Exception e)
      {
         e.printStackTrace();
         System.exit(1);
      }
   }

   private void go(String file1, String file2)
   {
      readFile2Vector(file1, v1);
      readFile2Vector(file2, v2);

      JiffFrame.getInstance().setTitle(" jiff   " + file1 + "   " + file2);
      jsp1 = JiffFrame.getInstance().getJScrollPane1();
      jsp2 = JiffFrame.getInstance().getJScrollPane2();

      jsp1.setViewportView(jp1);
      jsp2.setViewportView(jp2);
      jp1.setLayout(new GridLayout(0, 1));
      jp2.setLayout(new GridLayout(0, 1));

      while (v1.size() > 0 || v2.size() > 0)
      {
         compareFirstLines();
      }

      int fill = 50;
      /** If total number of JTextArea is less than the value of fill, 
       *  each JTextArea will have different hight, which makes 
       *  the display looks bad.
       */
      if (c1 < fill && c2 < fill)
      {
         for (int i = 0; i < fill - c1; i++)
         {
            addNoMatchToOne(null);
            addNoMatchToTwo(null);
         }
      }
      else
      // Prevents scrollbar from blocking the last line.
      {
         addNoMatchToOne(null);
         addNoMatchToTwo(null);
      }

      JiffFrame.getInstance().display();
   }

   private void compareFirstLines()
   {
      String s1 = null;
      String s2 = null;

      if (v2.size() == 0)
      {
         while (v1.size() != 0)
         {
            s1 = (String) v1.remove(0);
            newLineInOne(s1);
         }

         return;
      }

      if (v1.size() == 0)
      {
         while (v2.size() != 0)
         {
            s2 = (String) v2.remove(0);
            newLineInOne(s2);
         }

         return;
      }

      s1 = (String) v1.remove(0);
      s2 = (String) v2.remove(0);

      if (s1.equals(s2))
      {
         matched(s1);

         return;
      }

      if (s1.length() == 0)
      {
         newLineInOne(s1);
         v2.add(0, s2);

         return;
      }
      else if (s2.length() == 0)
      {
         newLineInTwo(s2);
         v1.add(0, s1);

         return;
      }

      if (!v1.contains(s2) && !v2.contains(s1))
      {
         notMatched(s1, s2);

         return;
      }

      if (!v1.contains(s2))
      {
         newLineInTwo(s2);
         v1.add(0, s1);

         return;
      }

      if (!v2.contains(s1))
      {
         newLineInOne(s1);
         v2.add(0, s2);

         return;
      }

      if (v2.contains(s1) && v1.contains(s2))
      {
         if (s1.length() > s2.length() || v1.indexOf(s2) > v2.indexOf(s1))
         {
            newLineInTwo(s2);
            v1.add(0, s1);
         }
         else
         {
            newLineInOne(s1);
            v2.add(0, s2);
         }

         return;
      }

      // Uncovered logic
      {
         notMatched(s1, s2);

         return;
      }
   }

   private void matched(String s)
   {
      addMatchToOne(s);
      addMatchToTwo(s);
   }

   private void notMatched(String s1, String s2)
   {
      addNoMatchToOne(s1);
      addNoMatchToTwo(s2);
   }

   private void newLineInOne(String s)
   {
      addNoMatchToOne(s);
      addNoMatchToTwo(null);
   }

   private void newLineInTwo(String s)
   {
      addNoMatchToOne(null);
      addNoMatchToTwo(s);
   }

   private void adjustNoMatchJTextArea(JTextArea t)
   {
      t.setForeground(Color.blue);
      //t.setBackground(Color.lightGray);
      t.setEditable(false);
      //t.setMaximumSize(t.getMinimumSize());
   }

   private void adjustMatchJTextArea(JTextArea t)
   {
      t.setEditable(false);
      t.setMaximumSize(t.getMinimumSize());
   }

   private void addNoMatchToOne(String s)
   {
      if (s == null)
      {
         s = "";
      }
      else
      {
         s = c1 + ":" + s;
         c1++;
      }

      JTextArea t = new JTextArea(s);
      adjustNoMatchJTextArea(t);
      jp1.add(t);
   }

   private void addNoMatchToTwo(String s)
   {
      if (s == null)
      {
         s = "";
      }
      else
      {
         s = c2 + ":" + s;
         c2++;
      }

      JTextArea t = new JTextArea(s);
      adjustNoMatchJTextArea(t);
      jp2.add(t);
   }

   private void addMatchToOne(String s)
   {
      JTextArea t = new JTextArea(c1 + ":" + s);
      c1++;
      adjustMatchJTextArea(t);
      jp1.add(t);
   }

   private void addMatchToTwo(String s)
   {
      JTextArea t = new JTextArea(c2 + ":" + s);
      c2++;
      adjustMatchJTextArea(t);
      jp2.add(t);
   }
}
