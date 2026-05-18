package com.aviall.webservice;


import java.io.*;
//import javax.xml.*;
import javax.xml.validation.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.Document;

/**
 * Hello world!
 *
 */
public class App 
{
   public static void validate(String xmlFileName, String schemaFileName)
   {
      try
      {
         // Parse an XML document into a DOM tree.
         DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         Document document = parser.parse(new File(xmlFileName));

         // Create a SchemaFactory capable of understanding WXS schemas.
         SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

         // Load a WXS schema, represented by a Schema instance.
         Source schemaFile = new StreamSource(new File(schemaFileName));
         Schema schema = factory.newSchema(schemaFile);

         // Create a Validator object, which can be used to validate
         // an instance document.
         Validator validator = schema.newValidator();

         // Validate the DOM tree.
         // It may throw SAXParseException
         validator.validate(new DOMSource(document));
      }
      catch (ParserConfigurationException e)
      {
         // exception handling"
         e.printStackTrace();
      }
      catch (SAXException e)
      {
         // exception handling - document not valid!
         e.printStackTrace();
         System.out.println("----------------------------------------");
         System.out.println(e.getMessage());
         System.out.println("----------------------------------------");
      }
      catch (IOException e)
      {
         // exception handling
         e.printStackTrace();
      }
   }
}
