package com.learn.bbb;


import java.io.File;
import java.text.DecimalFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class HelloTest {
    private static final Logger LOG = LogManager.getLogger();

    private static final String GroupedNumberTwoDecimal = "#,###,###,##0.00";

    public static String toGroupedNumberTwoDecimal(Number number) {
        if (number == null) {
            return "";
        }

        return new DecimalFormat(GroupedNumberTwoDecimal).format(number);
    }

    public static String toCurrencyTwoDecimal(Number number) {
        return "$" + toGroupedNumberTwoDecimal(number);
    }

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        String fileName = "C:\test\"" + null;

        File file = new File(fileName);
        if (file.isFile()) {

            LOG.info(file.getName());
            LOG.info(file.getAbsolutePath());
            LOG.info("total space: " + file.getTotalSpace());
            LOG.info(fileName);
            LOG.info("Yes. It is a file. file.exists() = " + file.exists());
        } else {
            LOG.info("It is not a file.");
        }

        LOG.info("End Test.");

    }
}
