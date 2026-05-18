package com.learn;


import org.apache.log4j.Logger;
import org.junit.Test;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;


public class CoherenceCache
{
   protected static final Logger LOG = Logger.getLogger(CoherenceCache.class);

   private NamedCache            namedCache;

   public void putCache()
   {
      namedCache = CacheFactory.getCache("VirtualCache");
      String key = "hello";
      namedCache.put(key, "Hello Cache");
   }

   public void retrieveCache()
   {
      String str = (String) namedCache.get("hello");
      LOG.info("Object: " + str);
   }

   @Test
   public void doTest()
   {
      putCache();
      retrieveCache();
   }

}
