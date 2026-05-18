package com.learn.servlet;


import java.net.URL;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class InitServlet
    extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger();

    private static boolean isSet = false;

    private static final Lock LOCK = new ReentrantLock();

    @Override
    public void init() {

        LOG.info("########### Inside InitServlet ###########.");

    }

    public void forLog4j1() {
        LOCK.lock();
        try {
            if (!isSet) {
                ClassLoader classLoader = this.getClass().getClassLoader();
                URL log4jConfig = classLoader.getResource("log4j.xml");
                if (log4jConfig != null) {
                    DOMConfigurator.configure(log4jConfig);
                    // PropertyConfigurator.configure(log4jConfig);
                    isSet = true;
                    LOG.info("Log4J configured.");
                } else {
                    System.out.println("ERROR: Unbale to find log4j.xml.");
                }
            }
        } finally {
            LOCK.unlock();
        }
    }
}
