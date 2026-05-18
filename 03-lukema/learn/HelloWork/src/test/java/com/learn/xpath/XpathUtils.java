package com.learn.xpath;


import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.learn.util.ClasspathUtils;


public class XpathUtils {

    static public Document getDocumentFromClasspath(String filenameInClasspath, boolean namespaceAware)
        throws ParserConfigurationException, SAXException, IOException {
        String path = ClasspathUtils.getPasthAsString(filenameInClasspath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(namespaceAware);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(path);
    }

    static public Document getDocumentFromClasspathNamespaceAware(String filenameInClasspath)
        throws ParserConfigurationException, SAXException, IOException {
        return getDocumentFromClasspath(filenameInClasspath, true);
    }

    static public Document getDocumentFromClasspathNotNamespaceAware(String filenameInClasspath)
        throws ParserConfigurationException, SAXException, IOException {
        return getDocumentFromClasspath(filenameInClasspath, false);
    }

    static private Object evaluate(Document document, String xpath, QName qname)
        throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xxpath = factory.newXPath();
        XPathExpression expr = xxpath.compile(xpath);
        return expr.evaluate(document, qname);
    }

    static public NodeList evaluateToNodeList(Document document, String xpath)
        throws XPathExpressionException {
        return NodeList.class.cast(evaluate(document, xpath, XPathConstants.NODESET));
    }

    static public Double evaluateToDouble(Document document, String xpath)
        throws XPathExpressionException {
        return Double.class.cast(evaluate(document, xpath, XPathConstants.NUMBER));
    }

    static public String evaluateToString(Document document, String xpath)
        throws XPathExpressionException {
        return String.class.cast(evaluate(document, xpath, XPathConstants.STRING));
    }

    static public Boolean evaluateToBoolean(Document document, String xpath)
        throws XPathExpressionException {
        return Boolean.class.cast(evaluate(document, xpath, XPathConstants.BOOLEAN));
    }
}
