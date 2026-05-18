package com.learn.proc;


import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.log4j.Logger;


@Resource(name = "MyResource")
public class MyResource
   implements Serializable
{
   private static final long     serialVersionUID = 1L;

   protected static final Logger LOG              = Logger.getLogger(MyResource.class);

   public void info()
   {
      LOG.info("MyResource.info() invoked.");
   }

}
