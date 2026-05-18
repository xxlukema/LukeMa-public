package com.learn;


import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


public class HelloWorld
{
   protected static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Hello World!");

      Map<String, Person> map = new HashMap<String, Person>();

      Person hong = new Person();
      map.put("hong", hong);

      Person luke = new Person();
      AbstractMap.SimpleImmutableEntry<String, Person> simpleImmutableEntry = new AbstractMap.SimpleImmutableEntry<String, Person>("luke", luke);

      map.put(simpleImmutableEntry.getKey(), simpleImmutableEntry.getValue());
      // map.entrySet().add(simpleImmutableEntry);

      Set<String> keySet = map.keySet();
      for (String key : keySet)
      {
         Person person = map.get(key);
         person.setAge(6);
      }

      for (String key : keySet)
      {
         Person person = map.get(key);
         LOG.info(key + ": " + person.getAge());
      }

   }
}
