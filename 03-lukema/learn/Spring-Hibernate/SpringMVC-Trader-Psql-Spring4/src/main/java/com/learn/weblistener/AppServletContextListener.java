package com.learn.weblistener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.util.JulConfigReader;


/**
 * Servlet listener to display server start up information. 
 *
 */
@WebListener
public class AppServletContextListener
    implements ServletContextListener {

    private static final Logger LOG = LogManager.getLogger();

    private String appName = "SpringMVC-Trader";

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        JulConfigReader.readConfig();

        StringBuilder sb = new StringBuilder();
        sb.append("\n******************************\n");
        sb.append(appName + ": Scheduler Started\n");
        sb.append("******************************\n");
        LOG.info(sb.toString());
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        StringBuilder sb = new StringBuilder();
        sb.append("******************************\n");
        sb.append(appName + ": Scheduler Stopped\n");
        sb.append("******************************\n");
        LOG.info(sb.toString());
    }

}
