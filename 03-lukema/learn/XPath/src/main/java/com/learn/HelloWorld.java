package com.learn;


import java.io.File;
import java.util.List;


public class HelloWorld {
    private static final Logger LOG = Logger.getLogger(HelloWorld.class);

    public static final String TARGET_DIR = "target";

    public static final String CLASSES_DIR = TARGET_DIR + "/classes";

    public static final String BASE_TYPE_FILE_NAME = "TypBaseType.xsd";

    public static final String DATA_TYPE_FILE_NAME = "TypDataType.xsd";

    public static final File xmlFileData = new File(CLASSES_DIR, DATA_TYPE_FILE_NAME);

    public static final File xmlFileBase = new File(CLASSES_DIR, BASE_TYPE_FILE_NAME);

    public static final File xmlFile = new File(CLASSES_DIR, "CsCalibrationParameter.xsd");

    public static void main(String[] args)
        throws Exception {
        LOG.info("Hello World!");

        String xpath = "//xsd:complexType[@name]/xsd:simpleContent/xsd:extension[@base]";

        //String xpath = "//xsd:complexType/xsd:extension";

        readXPathFromXmlFile(xpath, xmlFile);

        LOG.info("Finished with xml file: " + xmlFile.getName());
    }

    public static void readXPathFromXmlFile(String xpath, File xmlFile)
        throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(xmlFile);

        @SuppressWarnings("unchecked")
        List<Node> nodeList = document.selectNodes(xpath);
        LOG.info("nodeList.size() = " + nodeList.size());

        for (Node node : nodeList) {
            System.out.println();

            String value = node.getText();
            System.out.println("value: " + value);

            String path = node.getPath();
            System.out.println("path: " + path);

            String name = node.getName();
            System.out.println("name: " + name);

            Node baseAttribute = node.selectSingleNode("@base");

            value = baseAttribute.getText();
            System.out.println("value: " + value);

        }
    }

}
