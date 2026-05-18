package com.learn.classpath;


import java.net.URL;


public class ClassPathURL extends ClassPathResource
{
   public static URL getURL(String fileName)
   {
      return getResource(fileName);
   }
}

