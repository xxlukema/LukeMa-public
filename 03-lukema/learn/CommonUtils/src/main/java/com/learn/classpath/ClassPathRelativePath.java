package com.learn.classpath;


import java.net.URL;


public class ClassPathRelativePath
{
   private static final String TARGET_CLASSES = "target/classes";

   public static String getRelativePath(String fileName)
   {
      URL url = ClassPathURL.getURL(fileName);

      if (url != null)
      {
         String path = url.getPath();
         int pos = path.indexOf(TARGET_CLASSES);

         if (pos > -1)
         {
            String relativePath = path.substring(pos);

            return relativePath;
         }
      }

      return null;
   }
}
