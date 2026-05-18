package com.learn.file;


import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class FileTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        String fileName = "C:/tmp/my dir/1.txt";
        File file = new File(fileName);
        File parent = file.getParentFile();

        if (parent == null) {
            LOG.info("Parent is null.");
        } else {
            if (!parent.exists()) {
                parent.mkdirs();
                LOG.info("Parent created.");
            }
        }

        LOG.info("End Test.");

    }
}
