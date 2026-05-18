package com.learn.plugin;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;


/**
 * @goal getyahoodata
 * @description Get data from yahoo.com
 */
public class GetYahooDataMojo
   extends AbstractMojo
{
   /**
   * The greeting to display.
   * @parameter expression="${name}" default-value="Use -Dname=\"Luke Ma\""
   */
   protected String greeting;

   private String   yahooUrl  = "http://ichart.finance.yahoo.com/table.csv?s=";

   private String   startDate = "&g=d&a=0&b=1&c=2000&ignore=.csv";

   private String   endDate   = "&d=11&e=31&f=2095";

   private String   dir       = "target/data";

   public void execute()
      throws MojoExecutionException
   {
      getLog().info("Calling getyahoodata...");

      String one = System.getProperty("symbols.one");

      if (one != null)
      {
         String symbol = one.trim();

         if (symbol.length() > 0)
         {
            try
            {
               readData(symbol);
            }
            catch (Throwable t)
            {
               t.printStackTrace();
               throw new MojoExecutionException(t.getMessage());
            }
         }
      }
      else
      {
         try
         {
            File file = new File("symbols.properties");

            Properties props = new Properties();
            props.load(new FileInputStream(file));

            String all = props.getProperty("symbols.all");

            StringTokenizer st = new StringTokenizer(all, "} ${");

            while (st.hasMoreTokens())
            {
               String category = st.nextToken();

               System.out.println("\n  [get] Getting category " + category.substring(8) + "\n");

               String categorySymbols = props.getProperty(category);
               if (categorySymbols != null)
               {
                  String[] symbols = categorySymbols.split(" ");
                  for (int k = 0; k < symbols.length; k++)
                  {
                     String symbol = symbols[k].trim();

                     if (symbol.length() > 0)
                     {
                        readData(symbol);
                     }
                  }
               }
            }
         }
         catch (Throwable t)
         {
            t.printStackTrace();
            throw new MojoExecutionException(t.getMessage());
         }
      }
   }

   public void readData(String symbol)
      throws Exception
   {
      String urlString = yahooUrl + symbol + endDate + startDate;
      System.out.println("    [get] Getting: " + urlString);

      String outFileName = dir + "/" + symbol + ".csv";
      File outFile = new File(outFileName);
      System.out.println("    [get] To: " + outFile.getAbsoluteFile());

      URL url = new URL(urlString);
      URLConnection urlConn = url.openConnection();

      InputStream is = urlConn.getInputStream();

      new File(dir).mkdirs();

      byte[] buf = new byte[1000];

      FileOutputStream fos = new FileOutputStream(outFile, false);

      int len = 0;
      while ((len = is.read(buf, 0, buf.length)) > 0)
      {
         fos.write(buf, 0, len);
      }

      fos.close();
      is.close();

      System.out.println("    [get] Succeed.");
   }
}
