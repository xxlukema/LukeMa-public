package com.learn;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.cache.ContinuousQueryCache;
import com.tangosol.util.Filter;
import com.tangosol.util.extractor.IdentityExtractor;
import com.tangosol.util.filter.LikeFilter;


public class DatabaseCache
{
   protected static final Logger LOG = Logger.getLogger(DatabaseCache.class);

   private NamedCache            namedCache;

   public void createCache()
   {
      namedCache = CacheFactory.getCache("DBBackedCache");
   }

   public void addEntry()
   {
      namedCache.put("catalog3", "Tuning Grid Management");
      LOG.info("Added catalog3.");
   }

   public void retrieveEntry()
   {
      LOG.info("catalog1 = " + namedCache.get("catalog1"));
      LOG.info("catalog3 = " + namedCache.get("catalog3"));
   }

   public void eraseEntry()
   {
      namedCache.remove(new String("catalog3"));
      LOG.info("Removed catalog3.");
   }

   @SuppressWarnings("unchecked")
   public int queryCache()
   {
      Filter filter = new LikeFilter(IdentityExtractor.INSTANCE, "Tuning%", '\\', true);
      HashSet<String> hashSet = new HashSet<String>();
      hashSet.add(new String("catalog1"));
      hashSet.add(new String("catalog2"));
      hashSet.add(new String("catalog3"));

      ContinuousQueryCache queryCache = new ContinuousQueryCache(namedCache, filter);
      Set<String> results = queryCache.entrySet(filter);

      if (results.isEmpty())
      {
         LOG.info("Result Set Empty");
      }

      for (Iterator i = results.iterator(); i.hasNext();)
      {
         Map.Entry e = (Map.Entry) i.next();
         LOG.info("Catalog ID: " + e.getKey() + ", Title: " + e.getValue());
      }

      return results.size();
   }

   @Test
   public void testDB()
   {
      createCache();
      addEntry();
      retrieveEntry();

      int size = queryCache();
      Assert.assertEquals(1, size);

      eraseEntry();

      size = queryCache();
      Assert.assertEquals(0, size);
   }
}
