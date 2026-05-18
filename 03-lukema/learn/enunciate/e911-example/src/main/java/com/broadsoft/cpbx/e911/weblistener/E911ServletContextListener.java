package com.broadsoft.cpbx.e911.weblistener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.broadsoft.cpbx.e911.util.JulConfigReader;


@WebListener
public class E911ServletContextListener
    implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(E911ServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        JulConfigReader.readConfig();
        logger.info("e911-example service started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        logger.info("e911-example service stopped.");
    }

}
