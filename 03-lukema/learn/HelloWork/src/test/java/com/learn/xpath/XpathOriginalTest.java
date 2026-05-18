package com.learn.xpath;


import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lombok.extern.log4j.Log4j2;


/**
 * <https://howtodoinjava.com/java/xml/java-xpath-tutorial-example/>
 */
@Log4j2
public class XpathOriginalTest {

    @Test
    public void testRead()
        throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
        log.debug(() -> "Start");

        Document doc = XpathUtils.getDocumentFromClasspathNamespaceAware("inventory.xml");

        //Create XPath
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();

        /**
         * 1) Get book titles written after 2001
         */
        log.debug(() -> "1) Get book titles written after 2001");

        XPathExpression expr = xpath.compile("//book[@year>2001]/title/text()");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /*
         * 2) Get book titles written before 2001
         */
        log.debug(() -> "2) Get book titles written before 2001");

        expr = xpath.compile("//book[@year<2001]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /*
         * 3) Get book titles cheaper than 8 dollars
         */
        log.debug(() -> "3) Get book titles cheaper than 8 dollars");

        expr = xpath.compile("//book[price<8]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /**
         * 4) Get book titles costlier than 8 dollars
         */
        log.debug(() -> "4) Get book titles costlier than 8 dollars");

        expr = xpath.compile("//book[price>8]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /**
         * 5) Get book titles added in first node
         */
        log.debug(() -> "5) Get book titles added in first node");

        expr = xpath.compile("//book[1]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /**
         * 6) Get book title added in last node
         */
        log.debug(() -> "6) Get book title added in last node");

        expr = xpath.compile("//book[last()]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(
                    nodes.item(i).getNodeValue());
        }

        /**
         * 7) Get all writers
         */
        log.debug(() -> "7) Get all writers");

        expr = xpath.compile("//book/author/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(
                    nodes.item(i).getNodeValue());
        }

        /**
         * 8) Count all books titles
         */
        log.debug(() -> "8) Count all books titles");

        expr = xpath.compile("count(//book/title)");
        result = expr.evaluate(doc, XPathConstants.NUMBER);
        Double count = (Double) result;
        log.debug(count.intValue());

        /**
         * 9) Get book titles with writer name start with Neal
         */
        log.debug(() -> "9) Get book titles with writer name start with Neal");

        expr = xpath.compile("//book[starts-with(author,'Neal')]");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i)
                    .getChildNodes()
                    .item(1) //node <title> is on first index
                    .getTextContent());
        }

        /**
         * 10) Get book titles with writer name containing Niven
         */
        log.debug(() -> "10) Get book titles with writer name containing Niven");

        expr = xpath.compile("//book[contains(author,'Niven')]");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i)
                    .getChildNodes()
                    .item(1) //node <title> is on first index
                    .getTextContent());
        }

        /**
         * 11) Get book titles written by Neal Stephenson
         */
        log.debug(() -> "11) Get book titles written by Neal Stephenson");

        expr = xpath.compile("//book[author='Neal Stephenson']/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /**
         * 12) Get count of book titles written by Neal Stephenson
         */
        log.debug(() -> "12) Get count of book titles written by Neal Stephenson");

        expr = xpath.compile("count(//book[author='Neal Stephenson'])");
        result = expr.evaluate(doc, XPathConstants.NUMBER);
        count = (Double) result;
        log.debug(count.intValue());

        /**
         * 13) Reading comment node
         */
        log.debug(() -> "13) Reading comment node");

        expr = xpath.compile("//inventory/comment()");
        result = expr.evaluate(doc, XPathConstants.STRING);
        String comment = (String) result;
        log.debug(() -> comment);

        log.debug(() -> "End");
    }
}
