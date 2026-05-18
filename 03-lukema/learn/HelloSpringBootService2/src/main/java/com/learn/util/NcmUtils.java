package com.learn.util;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class NcmUtils {

    private static final Logger LOG = LogManager.getLogger();

    public static final String RestDateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String RestDateFormat = "yyyy-MM-dd";
    public static final String RestTimeFormat = "HH:mm:ss";

    public static final String ConfigPrefix = "com.freddiemac.ecert.config.";

    // @formatter:off
    public static final String[] Schemas = {  
        "xsd/xlink.xsd",
        "xsd/MISMO_3_0.xsd",
        "xsd/eCertification.xsd", 
        };
 // @formatter:on

    private static final ThreadLocal<SimpleDateFormat> NcmSimpleDateFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(RestDateTimeFormat);
        }
    };

    /**
     * Date formatter: Date to String
     * @param date
     * @return
     */
    public static String format(Date date) {
        return NcmSimpleDateFormat.get().format(date);
    }

    /**
     * Date formatter: String to Date
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date toDate(String date)
        throws ParseException {
        return NcmSimpleDateFormat.get().parse(date);
    }

    /**
     * Convert jaxb object to xml
     * @param t
     * @return
     * @throws JAXBException
     */
    public static <T> String jaxbObject2String(T t)
        throws JAXBException {

        StringWriter writer = new StringWriter();
        String theXML = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(t.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(t, writer);

            theXML = writer.toString();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                LOG.error("IOException", e);
            }
        }

        return theXML;
    }

    /**
     * Read file from current directory
     * @param path
     * @return
     */
    public static String readFileCurrentDir(String path) {

        FileReader fr = null;
        BufferedReader br = null;

        try {
            try {
                fr = new FileReader(path);
            } catch (FileNotFoundException e) {
                LOG.error("FileNotFoundException", e);
                return null;
            }

            br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            try {
                for (String line = null; (line = br.readLine()) != null;) {
                    sb.append(line);
                    sb.append("\n");
                }
            } catch (IOException e) {
                LOG.error("IOException", e);
            }
            return sb.toString();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                LOG.error("IOException", e);
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    LOG.error("IOException", e);
                }
            }
        }
    }

    /**
     * Convert xml to jaxb object
     * @param clazz
     * @param theXML
     * @return
     * @throws JAXBException
     * @throws SAXException
     */
    @SuppressWarnings("unchecked")
    public static <T> T string2JaxbObject(Class<T> clazz, String theXML)
        throws JAXBException, SAXException {

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schemaFactory.setResourceResolver(new MyLSResourceResolver());

        Source[] sources = new Source[Schemas.length];
        List<Source> sourceList = new ArrayList<Source>();
        List<InputStream> inputStreamList = new ArrayList<InputStream>();
        try {
            for (String xsd : NcmUtils.Schemas) {
                InputStream inputStream = newInputStream(xsd);
                inputStreamList.add(inputStream);
                Source source = new StreamSource(inputStream);
                sourceList.add(source);
            }
            sourceList.toArray(sources);

            StringReader reader = new StringReader(theXML);
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                Schema schema = schemaFactory.newSchema(sources);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                unmarshaller.setSchema(schema);
                unmarshaller.setEventHandler(new NcmValidationEventHandler());

                return (T) unmarshaller.unmarshal(reader);
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        } finally {
            for (InputStream inputStream : inputStreamList) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOG.error("IOException", e);
                }
            }
        }
    }

    /**
     * Validate xml string.
     * @param xml
     * @return
     * @throws SAXException
     */
    public static boolean validateECertXml(String xml)
        throws SAXException {

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schemaFactory.setResourceResolver(new MyLSResourceResolver());

        Source[] sources = new Source[Schemas.length];
        List<Source> sourceList = new ArrayList<Source>();
        List<InputStream> inputStreamList = new ArrayList<InputStream>();
        try {
            for (String xsd : NcmUtils.Schemas) {
                InputStream inputStream = newInputStream(xsd);
                inputStreamList.add(inputStream);
                Source source = new StreamSource(inputStream);
                sourceList.add(source);
            }
            sourceList.toArray(sources);

            Schema schema = schemaFactory.newSchema(sources);
            Validator validator = schema.newValidator();
            try {
                validator.validate(new StreamSource(new StringReader(xml)));
                return true;
            } catch (IOException e) {
                LOG.error("IOException", e);
            }
        } finally {
            for (InputStream inputStream : inputStreamList) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOG.error("IOException", e);
                }
            }
        }

        return false;
    }

    /**
     * Remove white spaces of a xml.
     * @param xml
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws TransformerException
     */
    public static String removeWhiteSpaces(String xml)
        throws ParserConfigurationException, SAXException, TransformerException {
        if (xml == null) {
            return xml;
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setIgnoringElementContentWhitespace(true);

        Document doc = null;
        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());

            StringReader sr = new StringReader(xml);
            InputSource is = new InputSource(sr);
            doc = builder.parse(is);

            Node root = doc.getFirstChild();
            trimWhitespace(root);

            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (IOException e) {
            LOG.error("IOException", e);
        }

        return null;
    }

    /**
     * Trim white spaces of nodes
     * @param node
     */
    private static void trimWhitespace(Node node) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE) {
                child.setTextContent(child.getTextContent().trim());
            }
            trimWhitespace(child);
        }
    }

    /**
     * Create a new Element of given name/value.
     * @param name
     * @param value
     * @return
     */
    public static Element newElement(String name, String value) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setIgnoringElementContentWhitespace(true);

        DocumentBuilder builder = null;
        try {
            builder = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOG.error("ParserConfigurationException", e);
        }

        if (builder != null) {
            builder.setErrorHandler(new MyErrorHandler());

            Document doc = builder.newDocument();

            Element element = doc.createElement(name);
            element.appendChild(doc.createTextNode(value));

            return element;
        }

        return null;
    }

    /**
     * Get InputStream of a given filename
     * @param fileName
     * @return
     */
    public static InputStream newInputStream(String fileName) {
        URL url = getResource(fileName);
        InputStream is = null;

        if (url != null) {
            try {
                is = url.openStream();
            } catch (Throwable th) {
                LOG.error("Unable to open the property file from CLASSPATH. " + fileName, th);
            }
        }

        return is;
    }

    /**
     * Get URL of a given file.
     * @param fileName
     * @return
     */
    public static URL getResource(String fileName) {
        if (fileName == null) {
            LOG.error("File name is null.");
            return null;
        }

        fileName = fileName.trim();

        if (fileName.length() == 0) {
            LOG.error("File name is empty.");
            return null;
        }

        URL url = null;

        ClassLoader cl = NcmUtils.class.getClassLoader();
        if (cl != null) {
            url = cl.getResource(fileName);
        } else {
            LOG.error("ClassLoader is null: " + NcmUtils.class.getName());
        }

        if (url == null) {
            cl = ClassLoader.getSystemClassLoader();
            if (cl != null) {
                url = cl.getResource(fileName);
            } else {
                LOG.error("System ClassLoader is null.");
            }
        }

        return url;
    }

    /**
     * Read file from classpath
     * @param path
     * @return
     */
    public static String readFile(String path) {

        InputStream inputStream = newInputStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        BufferedReader br = null;

        try {
            br = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            try {
                for (String line = null; (line = br.readLine()) != null;) {
                    sb.append(line);
                    sb.append("\n");
                }
            } catch (IOException e) {
                LOG.error("IOException", e);
            }
            return sb.toString();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                LOG.error("IOException", e);
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    LOG.error("IOException", e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOG.error("IOException", e);
                }
            }
        }
    }
}
