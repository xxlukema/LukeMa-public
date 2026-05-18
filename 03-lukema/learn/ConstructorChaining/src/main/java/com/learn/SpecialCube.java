package com.learn;


import org.apache.log4j.Logger;
import org.junit.Test;


public class SpecialCube
   extends Cube
{
   protected static final Logger LOG = Logger.getLogger(SpecialCube.class);

   private int                   weight;

   public SpecialCube()
   {
      super();
      weight = 10;
   }

   public SpecialCube(int l, int b)
   {
      this(l, b, 10);
      LOG.info("Finished with Parameterized Constructor having 2 params of SpecialCube");
   }

   public SpecialCube(int l, int b, int h)
   {
      super(l, b, h);
      weight = 20;
      LOG.info("Finished with Parameterized Constructor having 3 params of SpecialCube");
   }

   public static void main(String[] args)
   {
      SpecialCube specialObj1 = new SpecialCube();
      SpecialCube specialObj2 = new SpecialCube(10, 20);

      LOG.info("Volume of SpecialCube1 is : " + specialObj1.getVolume());
      LOG.info("Weight of SpecialCube1 is : " + specialObj1.weight);
      LOG.info("Volume of SpecialCube2 is : " + specialObj2.getVolume());
      LOG.info("Weight of SpecialCube2 is : " + specialObj2.weight);
   }

}
