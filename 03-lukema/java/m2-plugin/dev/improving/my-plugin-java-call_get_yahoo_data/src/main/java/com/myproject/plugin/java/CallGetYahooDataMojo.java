package com.myprject.plugin.java;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.util.*;
import java.io.*;
import java.net.*;

/**
 * @goal getyahoodata
 */
public class CallGetYahooDataMojo extends AbstractMojo 
{
   /**
   * The greeting to display.
   * @parameter expression="Hello"
   */
   private String greeting;

   private String yahooUrl  = "http://ichart.finance.yahoo.com/table.csvi?s=";
   private String startDate = "g=d&a=0&b=1&c=2000&ignore=.csv";
   private String endDate   = "d=11&e=31&f=2095";

   public void execute() throws MojoExecutionException 
   {
      getLog().info("Hello, world.");

      try
      {
         File file = new File("project.properties");

         Properties props = new Properties();
         props.load(new FileInputStream(file));

         String hot = props.getProperty("symbols.hot");

         String [] symbols = hot.split(" ");
         for (int i=0; i<symbols.length; i++)
         {
            String symbol = symbols[i].trim();

            if (symbol.length() > 0)
            {
               readData(symbol);
            }

            /*
            Process p = Runtime.getRuntime().exec("mvn getyahoodata:get -Dsymbol="+symbols[i]);
            // Process p = Runtime.getRuntime().exec("sleep 10");
            p.waitFor();
            */
         }

      }
      catch (Throwable t)
      {
         t.printStackTrace();
         throw new MojoExecutionException(t.getMessage());
      }
   }

   public void readData(String symbol)
   throws Exception
   {
      String urlString = yahooUrl + symbol + startDate + endDate;

      // urlString = "http://localhost:8080/";

      URL url = new URL(urlString);
      URLConnection urlConn = url.openConnection();

      InputStream is = urlConn.getInputStream();

      String dir = "target/data";
      new File(dir).mkdirs();
      File outFile = new File(dir+"/"+symbol+".csv");

      byte [] buf = new byte[1000];

      FileOutputStream fos = new FileOutputStream(outFile, false);

      int len = 0;
      while ((len = is.read(buf, 0, buf.length)) > 0)
      {
         fos.write(buf, 0, len);
      }

      fos.close();
      is.close();
   }
}
