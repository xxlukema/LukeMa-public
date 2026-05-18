package com.learn;


import org.apache.log4j.*;

/**
 * @author Fredrick Mjema
 * @version 0.2, 08/04/2003
 *
 * This class provides a log and trace facility to the APS to KYC  Interface
 * application. It is implemented as a wrapper around the Logging facility, to enable the underlying logging
 * framework to be changed without rewritting application code.
  */

public class LogHelper
{
   //Static variables
   static private boolean initialized = false;
   //Instance variables
   private Logger logger = null;
   public LogHelper(Class component)
   {
      super();
      //Initialize the logging system if required
      if (!initialized)
      {
         init();
      }
      // Register this component as a LOG4J Logger
      logger = Logger.getLogger(component);
   }
   //Register this component

   /**
    * Initialize the underlying logging system that this class wraps
    */
   private static synchronized void init()
   {
      if (!initialized)
      {
         // Use a Log4J PropertyConfigurator to load logging information from
         // a properties file. configureAndWatch will start a thread to
         // check the properties file every 60 seconds
         //PropertyConfigurator.configureAndWatch("log4j.properties",60000);
         //PropertyConfigurator.configureAndWatch("target/classes/log4j.properties");
         PropertyConfigurator.configureAndWatch("target/classes/log4j.xml");
         //initialized successfully
         initialized = true;
      }

   }
   /**
    * Logs informational message
    * @param o java.lang.Object The message to be written to the log
    */
   public void info(Object o)
   {
      //logger.info( o );
      logger.log("com.learn.LogHelper", Level.INFO, o, null);
   }
   /**
    * Logs an informational message including stack trace from an exception
    * @param o java.lang.Object The message to be written to the log
    * @param e java.lang.Exception The exception
    */
   public void info(Object o, Exception e)
   {
      logger.info( o, e );

   }
   /**
    * Logs debug message
    * @param o java.lang.Object The message to be written to the log
    */
   public void debug(Object o)
   {
      //logger.debug( o );
      logger.log("com.learn.LogHelper", Level.DEBUG, o, null);

   }
   /**
    * Logs an debug message including stack trace from an exception
    * @param o java.lang.Object The message to be written to the log
    * @param e java.lang.Exception The exception
    */
   public void debug(Object o, Exception e)
   {
      logger.debug( o, e );

   }     
   /**
    * Logs error message
    * @param o java.lang.Object The message to be written to the log
    */
   public void error(Object o)
   {
      logger.error( o );

   }
   /**
    * Logs an error message including stack trace from an exception
    * @param o java.lang.Object The message to be written to the log
    * @param e java.lang.Exception The exception
    */
   public void error(Object o, Exception e)
   {
      //logger.error( o, e );
      logger.log("com.learn.LogHelper", Level.ERROR, o, e);

   }  

   /**
    * Log error message using throwable.
    * @param o
    * @param e
    */
   public void error(Object o, Throwable e)
   {
      //logger.error( o, e );
      logger.log("com.learn.LogHelper", Level.ERROR, o, e);

   }  
   /**
    * Logs warning message
    * @param o java.lang.Object The message to be written to the log
    */
   public void warn(Object o)
   {
      logger.warn( o );

   }
   /**
    * Logs a warning message including stack trace from an exception
    * @param o java.lang.Object The message to be written to the log
    * @param e java.lang.Exception The exception
    */
   public void warn(Object o, Exception e)
   {
      logger.warn( o, e );

   }
}


