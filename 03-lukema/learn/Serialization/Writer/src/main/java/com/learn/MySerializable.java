package com.learn;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class MySerializable
   implements Serializable
{
   private static final long serialVersionUID = 0l;

   private Map<String, String> map = new HashMap<String, String>();
   
   public void init()
   {
      map.put("Luke Ma", "832-588-7811");
      map.put("Paul Cooley", "Don't know the number");
   }
   
   public Map<String, String> getMap()
   {
      return map;
   }
}
