package com.learn.xpath;


import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lombok.extern.log4j.Log4j2;


/**
 * <https://howtodoinjava.com/java/xml/java-xpath-tutorial-example/>
 */
@Log4j2
public class XpathTest {
    @Test
    public void testRead()
        throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
        log.debug(() -> "Start");

        Document document = XpathUtils.getDocumentFromClasspathNamespaceAware("inventory.xml");

        /**
         * 1) Get book titles written after 2001
         */
        log.debug(() -> "1) Get book titles written after 2001");
        String xpath = "//book[@year>2001]/title/text()";

        NodeList nodes = XpathUtils.evaluateToNodeList(document, xpath);
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /*
         * 2) Get book titles written before 2001
         */
        log.debug(() -> "2) Get book titles written before 2001");

        xpath = "//book[@year<2001]/title/text()";

        nodes = XpathUtils.evaluateToNodeList(document, xpath);
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /*
         * 3) Get book titles cheaper than 8 dollars
         */
        log.debug(() -> "3) Get book titles cheaper than 8 dollars");

        xpath = "//book[price<8]/title/text()";

        nodes = XpathUtils.evaluateToNodeList(document, xpath);
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /**
         * 4) Get book titles costlier than 8 dollars
         */
        log.debug(() -> "4) Get book titles costlier than 8 dollars");

        xpath = "//book[price>8]/title/text()";

        nodes = XpathUtils.evaluateToNodeList(document, xpath);
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /**
         * 5) Get book titles added in first node
         *    Trick: [1 indexed]
         */
        log.debug(() -> "5) Get book titles added in first node");

        xpath = "//book[1]/title/text()";

        nodes = XpathUtils.evaluateToNodeList(document, xpath);
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /**
         * 6) Get book title added in last node
         */
        log.debug(() -> "6) Get book title added in last node");

        xpath = "//book[last()]/title/text()";

        nodes = XpathUtils.evaluateToNodeList(document, xpath);
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /**
         * 7) Get all writers
         *    Trick: One book can have two authors
         */
        log.debug(() -> "7) Get all writers");

        xpath = "//book/author/text()";

        nodes = XpathUtils.evaluateToNodeList(document, xpath);
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /**
         * 8) Count all books titles
         */
        log.debug(() -> "8) Count all books titles");

        xpath = "count(//book/title)";

        // result = expr.evaluate(document, XPathConstants.NUMBER);
        Double count = XpathUtils.evaluateToDouble(document, xpath);
        log.debug(count.intValue());

        /**
         * 9) Get book titles with writer name start with Neal
         *    Trick: First item index == 1
         */
        log.debug(() -> "9) Get book titles with writer name start with Neal");

        xpath = "//book[starts-with(author,'Neal')]";

        nodes = XpathUtils.evaluateToNodeList(document, xpath);
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

        xpath = "//book[contains(author,'Niven')]";

        nodes = XpathUtils.evaluateToNodeList(document, xpath);
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

        xpath = "//book[author='Neal Stephenson']/title/text()";

        nodes = XpathUtils.evaluateToNodeList(document, xpath);
        for (int i = 0; i < nodes.getLength(); i++) {
            log.debug(nodes.item(i).getNodeValue());
        }

        /**
         * 12) Get count of book titles written by Neal Stephenson
         */
        log.debug(() -> "12) Get count of book titles written by Neal Stephenson");

        xpath = "count(//book[author='Neal Stephenson'])";

        // result = expr.evaluate(document, XPathConstants.NUMBER);
        count = XpathUtils.evaluateToDouble(document, xpath);
        log.debug(count.intValue());

        /**
         * 13) Reading comment node
         */
        log.debug(() -> "13) Reading comment node");

        xpath = "//inventory/comment()";

        // result = expr.evaluate(document, XPathConstants.STRING);
        String comment = XpathUtils.evaluateToString(document, xpath);
        log.debug(() -> comment);

        log.debug(() -> "End");
    }
}
