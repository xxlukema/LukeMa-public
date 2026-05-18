package com.learn;


import org.apache.log4j.Logger;


public class OneMegaByte
{
   protected static final Logger LOG             = Logger.getLogger(OneMegaByte.class);

   private byte[]                data            = new byte[1000000];

   public static int            InstanceCounter = 0;

   private int                   id;

   public OneMegaByte()
   {
      id = InstanceCounter++;
      
      LOG.info("OneMegaByte constructed: " + id);
   }

   public void setData(byte[] data)
   {
      this.data = data;
   }

   public byte[] getData()
   {
      return data;
   }

   @Override
   public void finalize()
   {
      LOG.info("\t ~OneMegaByte detroyed: " + id);
   }

   public void setId(int id)
   {
      this.id = id;
   }

   public int getId()
   {
      return id;
   }
}
