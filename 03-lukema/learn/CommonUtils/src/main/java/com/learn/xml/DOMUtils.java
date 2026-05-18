package com.learn.xml;


import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.io.DOMReader;
import org.xml.sax.InputSource;


public class DOMUtils
{
   public static String document2String(org.w3c.dom.Document document)
      throws Exception
   {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();

      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

      try
      {
         transformerFactory.setAttribute("indent-number", 2);
      }
      catch (IllegalArgumentException iae)
      {
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
      }

      //create string from xml tree
      StringWriter stringWriter = new StringWriter();
      StreamResult streamResult = new StreamResult(stringWriter);
      DOMSource domSource = new DOMSource(document);
      transformer.transform(domSource, streamResult);
      String xmlString = stringWriter.toString();

      return xmlString;
   }

   public static org.dom4j.Document parseDOM2Dom4j(org.w3c.dom.Document doc)
      throws Exception
   {
      if (doc == null)
      {
         return null;
      }
      
      DOMReader xmlReader = new DOMReader();
      
      return xmlReader.read(doc);
   }

   public static org.w3c.dom.Document parseDom4j2DOM(org.dom4j.Document doc)
      throws Exception
   {
      if (doc == null)
      {
         return null;
      }
      
      StringReader stringReader = new StringReader(doc.asXML());
      InputSource inputSource = new InputSource(stringReader);
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      
      return documentBuilder.parse(inputSource);
   }
}
