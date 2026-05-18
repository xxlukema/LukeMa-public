package com.learn.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.util.*;
import java.io.*;
import java.net.*;

/**
 * @goal jsp_precompile
 * @description Requests wer server to precompiles jsp pages of given directory.
 */
public class PrecompileJSPMojo
extends AbstractMojo 
{
   /**
     * @parameter default-value="src/main/webapp"
     */
   private String localJspDir = null;

   /**
     * @parameter default-value="http://localhost:8080/learn-jsp"
     */
   private URL targetBaseURL = null;

   private static String JSP_PRECOMPILE_OPTION = "?jsp_precompile";

   private final SortedMap JSP_COMPILED_MAP = new TreeMap();
   private final SortedMap JSP_ERRORED_MAP  = new TreeMap();

   public void execute()
   throws MojoExecutionException 
   {
      normalizeConfigurationParameters();

      getLog().info("Calling precompile JSP...\n");

      getLog().info("targetBaseURL: "+targetBaseURL);
      getLog().info("localJspDir:   "+localJspDir);

      File file = new File(localJspDir);
      if (file.isDirectory())
      {
         SortedSet jspFileSet = findAllJspFileNamesFromDir(localJspDir);

         getLog().info("JSP file to precompile ("+jspFileSet.size()+"):\n");

         for (Iterator it=jspFileSet.iterator(); it.hasNext(); )
         {
            String fileName = (String) it.next();
            if (fileName != null)
            {
               System.out.println("      "+fileName);
            }
         }

         SortedMap jspUrlMap  = composeTargetURLs(jspFileSet);

         doPrecompile(jspUrlMap);

         preCompileSummary();
      }
      else
      {
         throw new MojoExecutionException("localJspDir: "+localJspDir+" not found.");
      }
   }

   private void normalizeConfigurationParameters()
   {
      if (localJspDir != null)
      {
         localJspDir = localJspDir.trim();
      }

      if ( !localJspDir.endsWith("/") )
      {
         localJspDir += "/";
      }

      String strTargetBaseURL = targetBaseURL.toString();
      if ( !strTargetBaseURL.endsWith("/") )
      {
         strTargetBaseURL += "/";
      }

      try
      {
         targetBaseURL = new URL(strTargetBaseURL);
      }
      catch (Exception e)
      {
         getLog().error(e);
      }
   }

   private void preCompileSummary()
   {
      System.out.println("");

      getLog().info("JSPs successfully precompiled ("+JSP_COMPILED_MAP.size()+"): ");

      Set keySet = JSP_COMPILED_MAP.keySet();
      for (Iterator it=keySet.iterator(); it.hasNext(); )
      {
         String jspFileName = (String) it.next();
         System.out.println("      "+jspFileName);
      }

      getLog().info("JSPs precompile with errors ("+JSP_ERRORED_MAP.size()+"): ");

      keySet = JSP_ERRORED_MAP.keySet();
      for (Iterator it=keySet.iterator(); it.hasNext(); )
      {
         String jspFileName = (String) it.next();
         System.out.println("    * "+jspFileName);
      }
   }

   private SortedSet findAllJspFileNamesFromDir(String dirName)
   {
      SortedSet fileNameSet = new TreeSet();

      dirName = dirName.trim();

      if (dirName.endsWith("target/") || dirName.endsWith("tmp/") || dirName.endsWith("fix/") || dirName.endsWith("build/") || dirName.endsWith("dist/"))
      {
         return fileNameSet;
      }

      File file = new File(dirName);

      if (file.isDirectory())
      {
         String [] fileNames = file.list();

         for (int i=0; i<fileNames.length; i++)
         {
            String fileName = dirName+fileNames[i];

            if (fileName.endsWith(".jsp"))
            {
               String trimedFileName = fileName.replaceFirst(localJspDir, "");
               fileNameSet.add(trimedFileName);
            }
            else
            {
               SortedSet subSet = findAllJspFileNamesFromDir(fileName+"/");

               if (subSet != null && subSet.size() > 0)
               {
                  fileNameSet.addAll(subSet);
               }
            }
         }
      }

      return fileNameSet;
   }

   private SortedMap composeTargetURLs(SortedSet jspFileNameSet)
   {
      SortedMap jspUrlMap = new TreeMap();

      for (Iterator it=jspFileNameSet.iterator(); it.hasNext(); )
      {
         String jspFileName = (String) it.next();
         String targetURL = targetBaseURL+jspFileName+JSP_PRECOMPILE_OPTION;

         jspUrlMap.put(jspFileName, targetURL);
      }

      return jspUrlMap;
   }

   public void doPrecompile(SortedMap jspUrlMap)
   throws MojoExecutionException 
   {
      System.out.println("");

      Set jspFileNameSet = jspUrlMap.keySet();

      for (Iterator it=jspFileNameSet.iterator(); it.hasNext(); )
      {
         String jspFileName = (String) (it.next());
         String targetURL = (String) (jspUrlMap.get(jspFileName));

         if (targetURL != null)
         {
            getLog().info("Precompiling: "+jspFileName);

            callUrl(jspFileName, targetURL);
         }
      }
   }

   private void callUrl(String jspFileName, String targetUrl)
   throws MojoExecutionException 
   {
      HttpURLConnection httpConn = null;

      try
      {
         URLConnection urlConn = null;
         URL url = new URL(targetUrl);
         urlConn = url.openConnection();
         httpConn = (HttpURLConnection) urlConn;
      }
      catch (Exception e)
      {
         getLog().error("Unable to open connection to "+targetUrl);
         getLog().error(e);

         throw new MojoExecutionException(e.getMessage());
      }

      // Set the appropriate HTTP parameters.
      httpConn.setRequestProperty("Content-Length", String.valueOf(0));
      httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
      httpConn.setDoOutput(true);
      httpConn.setDoInput(true);

      OutputStream out = null;

      try
      {
         try
         {
            httpConn.setRequestMethod("GET");

            out = httpConn.getOutputStream();

            out.flush();
         }
         catch (Exception e)
         {
            getLog().error("Exception sending request: "+e.getMessage());
            getLog().error("\n\n      CHECK SERVER AVAILABLITY: "+targetBaseURL+"\n");

            throw new MojoExecutionException(e.getMessage());
         }
         finally
         {
            if (out != null)
            {
               try
               {
                  out.close();
               }
               catch (Exception e)
               {
                  getLog().error(e);
               }
            }
         }

         int code = 0;
         try
         {
            code = httpConn.getResponseCode();
         }
         catch (Exception e)
         {
            getLog().error(e);
         }

         InputStream  is  = null;
         boolean isDataFromInputStream = true;

         if (code == 200)
         {
            JSP_COMPILED_MAP.put(jspFileName, targetUrl);

            return;
         }
         else if (code == 404)
         {
            getLog().error("JSP not deployed to server: "+jspFileName);

            JSP_ERRORED_MAP.put(jspFileName, targetUrl);

            return;
         }
         else if (code == 500)
         {
            getLog().error("Server internal error with: "+jspFileName);

            is = httpConn.getErrorStream();
            isDataFromInputStream = false;

            JSP_ERRORED_MAP.put(jspFileName, targetUrl);
         }
         else
         {
            getLog().error("UNKNOWN ERROR precompiling JSP: "+jspFileName);

            JSP_ERRORED_MAP.put(jspFileName, targetUrl);

            try
            {
               is  = httpConn.getInputStream();
            }
            catch (IOException e)
            {
               getLog().error(e.getMessage());

               is = httpConn.getErrorStream();
               isDataFromInputStream = false;
            }
         }

         if (is == null)
         {
            getLog().info("No stream data to read.");

            return;
         }

         byte [] buf = new byte[100];

         try
         {
            if (isDataFromInputStream)
            {
               System.out.print("\n   Data from InputStrea: ");
            }
            else
            {
               System.out.print("\n   Data from ErrorStream: ");
            }

            int len = 0;
            while ((len = is.read(buf, 0, buf.length)) > 0)
            {
               System.out.write(buf, 0, len);
            }

            System.out.println("\n");

         }
         catch (Throwable e)
         {
            getLog().error(e);
         }
         finally
         {
            if (is != null)
            {
               try
               {
                  is.close();
               }
               catch (Throwable t)
               {
                  t.printStackTrace();
               }
            }
         }
      }
      finally
      {
         closeHttpConnection(httpConn);
      }
   }

   private void closeHttpConnection(HttpURLConnection httpConn)
   {
      if (httpConn != null)
      {
         try
         {
            httpConn.disconnect();
         }
         catch (Throwable t)
         {
            getLog().error(t);
            t.printStackTrace();
         }

         httpConn = null;
      }
   }
}

