package com.learn.xml;


import java.io.InputStream;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.learn.io.FileIOStreamHelper;


public class XMLBeautifier
{
   private static final Logger LOG = Logger.getLogger(XMLBeautifier.class);

   /**
    * @param xml - original xml string
    * @param whiteSpaceCollapse - true: REMOVE all white space \
                                        characters (line feeds, tabs, spaces, \
                                        carriage returns are replaced with \
                                        spaces, leading and trailing spaces \
                                        are removed, and multiple spaces are \
                                        reduced to a single space).
    * @return beautified xml string
    */
   public static String beautify(String xml, boolean whiteSpaceCollapse)
   throws Exception
   {
      if (xml == null)
      {
         return null;
      }

      StringWriter writer = new StringWriter();

      Document document = null;

      try
      {
         document = DocumentHelper.parseText(xml);
      }
      catch (Exception e)
      {
         LOG.error(e);

         String exceptionMessage = 
            "############## Begin bad XML ##############\n"+
            xml+"\n"+
            "############## End bad XML ##############\n";

         LOG.error(exceptionMessage);

         throw e;
      }

      OutputFormat format = OutputFormat.createPrettyPrint();
      format.setTrimText(whiteSpaceCollapse);
      format.setIndent(true);
      format.setIndentSize(3);
      XMLWriter xmlWriter = new XMLWriter(writer, format);
      xmlWriter.write(document);
      xmlWriter.close();

      return writer.toString();
   }

   /**
    * @param xml - original xml string  REMOVE all white space \
                                        characters (line feeds, tabs, spaces, \
                                        carriage returns are replaced with \
                                        spaces, leading and trailing spaces \
                                        are removed, and multiple spaces are \
                                        reduced to a single space).
    * @return beautified xml string
    */
   public static String beautify(String xml)
   throws Exception
   {
      return beautifyWhiteSpaceCollapse(xml);
   }

   public static String readClassPathFileBeautify(String fileName)
   throws Exception
   {
      String xml = FileIOStreamHelper.readClassPathFile2String(fileName);
      
      return beautify(xml);
   }

   public static String readInputStreamBeautify(InputStream is)
   throws Exception
   {
      String xml = FileIOStreamHelper.readInputStream2String(is);
      
      return beautify(xml);
   }

   /**
    * @param xml - original xml string  KEEK all white space \
                                        characters (line feeds, tabs, spaces, \
                                        carriage. \
    * @return beautified xml string
    */
   public static String beautifyKeepWhiteSpace(String xml)
   throws Exception
   {
      return beautify(xml, false);
   }

   /**
    * @param xml - original xml string  REMOVE all white space \
                                        characters (line feeds, tabs, spaces, \
                                        carriage returns are replaced with \
                                        spaces, leading and trailing spaces \
                                        are removed, and multiple spaces are \
                                        reduced to a single space).
    * @return beautified xml string
    */
   public static String beautifyWhiteSpaceCollapse(String xml)
   throws Exception
   {
      return beautify(xml, true);
   }
}

