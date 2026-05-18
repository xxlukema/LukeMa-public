package com.learn.xml;


import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.learn.classpath.ClassPathURL;


/**
  * dom4j needs jaxen as parser.
  *
  */
public class Dom4jUtils 
{
   private static final Logger LOG    = Logger.getLogger(Dom4jUtils.class);

   private Document document = null;


   public Dom4jUtils(String xmlFileName)
   {
      URL url = ClassPathURL.getURL(xmlFileName);

      try
      {
         SAXReader reader = new SAXReader();
         document = reader.read(url);
      }
      catch (Throwable t)
      {
         LOG.error("Error reading config file: "+xmlFileName);
         LOG.error(t);
      }
   }

   public String getText(String xpath)
   {
      return getTextFromLastNode(xpath);
   }

   public String getXML()
   {
      try
      {
         return document.asXML();
      }
      catch (Throwable t)
      {
         LOG.error(t);
      }

      return null;
   }

   public String getXML(String xpath)
   {
      Node node = getLastNode(xpath);

      if (node != null)
      {
         try
         {
            return node.asXML();
         }
         catch (Throwable t)
         {
            LOG.error(t);
         }
      }

      return null;
   }

   public int getInteger(String xpath)
   {
      int integer = 0;

      String text = getText(xpath);
      if (text != null)
      {
         try
         {
            integer = Integer.parseInt(text);
         }
         catch (Throwable t)
         {
            LOG.error(t);
         }
      }

      return integer;
   }

   public boolean getBoolean(String xpath)
   {
      boolean tf = false;

      String text = getText(xpath);
      if (text != null)
      {
         try
         {
            tf = Boolean.parseBoolean(text);
         }
         catch (Throwable t)
         {
            LOG.error(t);
         }
      }

      return tf;
   }

   public void info()
   {
      String xml = null;

      try
      {
         xml = XMLBeautifier.beautify(getXML());
      }
      catch (Throwable t)
      {
         LOG.error(t);
      }

      LOG.info(xml);
   }

   public void log()
   {
      info();
   }

   public String getTextFromFirstNode(String xpath)
   {
      Node node = getFirstNode(xpath);

      if (node != null)
      {
         return getTextFromNode(node);
      }

      return null;
   }

   public String getTextFromLastNode(String xpath)
   {
      Node node = getLastNode(xpath);

      if (node != null)
      {
         return getTextFromNode(node);
      }

      return null;
   }

   /**
     * @return first node for the xpath. returns null if no match.
     */
   public Node getFirstNode(String xpath)
   {
      try
      {
         return document.selectSingleNode(xpath);
      }
      catch (Throwable t)
      {
         LOG.error(t);
      }

      LOG.info("Node not found for XPATH: "+xpath);

      return null;
   }

   /**
     * @return last node for the xpath. returns null if no match.
     */
   public Node getLastNode(String xpath)
   {
      try
      {
         @SuppressWarnings("unchecked")
         List<Node> nodeList = document.selectNodes(xpath);

         if (nodeList.size() > 0)
         {
            return (Node) nodeList.get(nodeList.size() - 1);
         }
      }
      catch (Throwable t)
      {
         LOG.error(t);
      }

      LOG.info("Node not found for XPATH: "+xpath);

      return null;
   }

   public static String getTextFromNode(Node node)
   {
      if (node != null)
      {
         try
         {
            return node.getText();
         }
         catch (Throwable t)
         {
            LOG.error(t);
         }
      }

      return null;
   }
}

