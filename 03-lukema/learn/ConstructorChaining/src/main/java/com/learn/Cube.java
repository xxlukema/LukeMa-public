package com.learn;


import org.apache.log4j.Logger;


public class Cube
{
   protected static final Logger LOG = Logger.getLogger(Cube.class);

   private int                   length;

   private int                   breadth;

   private int                   height;

   public int getVolume()
   {
      return (length * breadth * height);
   }

   public Cube()
   {
      this(10, 10);
      LOG.info("Finished with Default Constructor of Cube");
   }

   public Cube(int l, int b)
   {
      this(l, b, 10);
      LOG.info("Finished with Parameterized Constructor having 2 params of Cube");
   }

   public Cube(int l, int b, int h)
   {
      length = l;
      breadth = b;
      height = h;
      LOG.info("Finished with Parameterized Constructor having 3 params of Cube");
   }
}
