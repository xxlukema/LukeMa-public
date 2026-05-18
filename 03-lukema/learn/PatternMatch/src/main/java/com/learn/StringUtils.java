package com.learn;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils
{
   protected static final Pattern LINE_NUMBER = Pattern.compile("(^[0-9]+).*");

   public static String find(String line, Pattern pattern)
   {
      if (line == null || line.length() == 0 || pattern == null)
      {
         return null;
      }

      System.out.println("############# line = " + line);

      Matcher matcher = pattern.matcher(line);

      if (matcher.find())
      {
         return matcher.group(1);
      }
      else
      {
         return null;
      }
   }

   public static String find(String line, Pattern pattern, int group)
   {
      if (line == null || line.length() == 0 || pattern == null)
      {
         return null;
      }

      Matcher matcher = pattern.matcher(line);

      return matcher.group(group);
   }

   public static String findLineNumber(String line)
   {
      return find(line, LINE_NUMBER);
   }
}
