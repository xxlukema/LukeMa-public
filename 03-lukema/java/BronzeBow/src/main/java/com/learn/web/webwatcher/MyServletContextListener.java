package com.learn.web.webwatcher;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.util.JulConfigReader;


@WebListener
public class MyServletContextListener
    implements ServletContextListener {

    /**
     * Change Classpath 
     * 
     */
    /*
    static {
    	Properties prop = new Properties();
    	String propFile = "/project.properties";
    	InputStream inputStream = WebWatchServletContextListener.class.getResourceAsStream(propFile);
    	if (inputStream != null) {
    		try {
    			prop.load(inputStream);
    		} catch (SecurityException | IOException e1) {
    			e1.printStackTrace();
    		}
    	}
    
    	String configDir = prop.getProperty("config.dir");
    	if (configDir != null) {
    		File dirToAdd = new File(configDir);
    
    		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
    
    		try {
    			@SuppressWarnings("unchecked")
    			Class<URL>[] parameters = new Class[] { URL.class };
    			Class<URLClassLoader> sysclass = URLClassLoader.class;
    			Method method = sysclass.getDeclaredMethod("addURL", parameters);
    			method.setAccessible(true);
    			method.invoke(sysloader, new Object[] { dirToAdd.toURI().toURL() });
    		} catch (Throwable t) {
    			t.printStackTrace();
    		}
    	}
    }
    */

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent event) {
        JulConfigReader.readConfig();
        logger.info("BronzeBow service started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        logger.info("BronzeBow service stopped.");
    }

}
