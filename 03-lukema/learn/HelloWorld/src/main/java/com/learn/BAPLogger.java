/*
 * Created on Mar 11, 2004
 */
package com.learn;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BAP logging utility that wraps around a Log4j logger.<br>
 * 
 * <p>
 * <b>It is recommended that the overridden methods that take className and
 * methodName as arguments be used only sparingly for performance reasons.</b>
 * 
 * @author bobbymantha
 * @author tangira
 */
public class BAPLogger {

	static final String LOGGER_MSG_DELIMITER_FORMAT = ": ";
	private Logger logger;

	protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(BAPLogger.class);

	/**
	 * @param loggerName
	 *            the name of the logger to create
	 * @return an instance of the logger
	 */
	public static BAPLogger getBAPLogger(String loggerName) {
		return new BAPLogger(loggerName);
	}

	/**
	 * @param loggerClass
	 *            the class from which the logger name will be derived
	 * @return an instance of the logger
	 */
	public static BAPLogger getBAPLogger(Class<?> loggerClass) {
		return new BAPLogger(loggerClass.getName());
	}

	/**
	 * Wrapper around debug(), checks to see if debug is enabled before logging
	 * the message
	 * 
	 * @param className
	 *            the class from which the message is logged
	 * @param methodName
	 *            the method from which the message is logged
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @param arg1
	 *            the throwable info to append
	 * @see org.apache.log4j.Category#debug(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	public void debug(String className, String methodName, Object arg0,
			Throwable arg1) {
		LOG.debug(logger.getName() + ": " + arg1);
		if (isDebugEnabled() && arg0 != null)
			// logger.debug(getMsgString(className, methodName) + arg0, arg1);
			logger.log(Level.FINEST,
					getMsgString(className, methodName) + arg0, arg1);
	}

	/**
	 * Wrapper around debug(), checks to see if debug is enabled before logging
	 * the message
	 * 
	 * @param className
	 *            the class from which the message is logged
	 * @param methodName
	 *            the method from which the message is logged
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @see org.apache.log4j.Category#debug(java.lang.Object)
	 */
	public void debug(String className, String methodName, Object arg0) {
		LOG.debug(logger.getName() + ": " + arg0);
		if (isDebugEnabled() && arg0 != null) // logger.debug(getMsgString(className,
												// methodName) + arg0);
			logger.log(Level.FINEST, getMsgString(className, methodName) + arg0);
	}

	/**
	 * Wrapper around debug(), checks to see if debug is enabled before logging
	 * the message
	 * 
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @see org.apache.log4j.Category#debug(java.lang.Object)
	 */
	public void debug(Object arg0) {
		LOG.debug(getCallerDetails() + arg0);

		if (isDebugEnabled() && arg0 != null) // logger.debug(arg0);
			logger.finest(arg0.toString());
	}

	private String getCallerDetails() {
		Exception exception = new Exception();

		StackTraceElement[] stackTraces = exception.getStackTrace();

		// StackTraceElement[] stackTraces =
		// Thread.currentThread().getStackTrace();

		StackTraceElement ste = null; // stackTraces[3];

		for (StackTraceElement s : stackTraces) {
			if (!s.getClassName().endsWith(".BAPLogger")) {
				ste = s;
				break;
			}
		}

		StringBuilder sb = new StringBuilder().append(ste.getClassName());

		sb.append("(").append(+ste.getLineNumber()).append(") ")
				.append(ste.getMethodName()).append("()\n");

		return sb.toString();
	}

	/**
	 * Wrapper around debug(), checks to see if debug is enabled before logging
	 * the message
	 * 
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @param arg1
	 *            the throwable info to append
	 * @see org.apache.log4j.Category#debug(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	public void debug(Object arg0, Throwable arg1) {
		LOG.debug(arg1);
		if (isDebugEnabled() && arg0 != null) // logger.debug(arg0, arg1);
			logger.log(Level.FINEST, arg0.toString(), arg1);
	}

	/**
	 * Wrapper around error()
	 * 
	 * @param className
	 *            the class from which the message is logged
	 * @param methodName
	 *            the method from which the message is logged
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @param arg1
	 *            the throwable info to append
	 * @see org.apache.log4j.Category#error(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	public void error(String className, String methodName, Object arg0,
			Throwable arg1) {
		LOG.error(logger.getName() + ": " + arg0, arg1);
		// logger.error(getMsgString(className, methodName) + arg0, arg1);
		if (arg0 != null)
			logger.log(Level.SEVERE,
					getMsgString(className, methodName) + arg0, arg1);
	}

	/**
	 * Wrapper around error()
	 * 
	 * @param className
	 *            the class from which the message is logged
	 * @param methodName
	 *            the method from which the message is logged
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @see org.apache.log4j.Category#error(java.lang.Object)
	 */
	public void error(String className, String methodName, Object arg0) {
		LOG.error(logger.getName() + ": " + arg0);
		// logger.error(getMsgString(className, methodName) + arg0);
		if (arg0 != null)
			logger.log(Level.SEVERE, getMsgString(className, methodName) + arg0);
	}

	/**
	 * Wrapper around error()
	 * 
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @see org.apache.log4j.Category#error(java.lang.Object)
	 */
	public void error(Object arg0) {
		// logger.error(arg0);
		LOG.error(logger.getName() + ": " + arg0);
		if (arg0 != null)
			logger.severe(arg0.toString());
	}

	/**
	 * Wrapper around error()
	 * 
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @param arg1
	 *            the throwable info to append
	 * @see org.apache.log4j.Category#error(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	public void error(Object arg0, Throwable arg1) {
		// logger.error(arg0, arg1);
		LOG.error(logger.getName() + ": " + arg0, arg1);
		if (arg0 != null)
			logger.log(Level.SEVERE, arg0.toString(), arg1);
	}

	/**
	 * Wrapper around fatal()
	 * 
	 * @param className
	 *            the class from which the message is logged
	 * @param methodName
	 *            the method from which the message is logged
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @param arg1
	 *            the throwable info to append
	 * @see org.apache.log4j.Category#fatal(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	public void fatal(String className, String methodName, Object arg0,
			Throwable arg1) {
		LOG.error(logger.getName() + ": " + arg0, arg1);
		// logger.fatal(getMsgString(className, methodName) + arg0, arg1);
		// logger.log(Level.SEVERE, getMsgString(className, methodName) + arg0,
		// arg1);
		if (arg0 != null)
			error(className, methodName, arg0, arg1);
	}

	/**
	 * Wrapper around fatal()
	 * 
	 * @param className
	 *            the class from which the message is logged
	 * @param methodName
	 *            the method from which the message is logged
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @see org.apache.log4j.Category#fatal(java.lang.Object)
	 */
	public void fatal(String className, String methodName, Object arg0) {
		LOG.error(logger.getName() + ": " + arg0);
		// logger.fatal(getMsgString(className, methodName) + arg0);
		if (arg0 != null)
			error(className, methodName, arg0);
	}

	/**
	 * Wrapper around fatal()
	 * 
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @see org.apache.log4j.Category#fatal(java.lang.Object)
	 */
	public void fatal(Object arg0) {
		LOG.error(logger.getName() + ": " + arg0);
		// logger.fatal(arg0);
		if (arg0 != null)
			logger.severe(arg0.toString());
	}

	/**
	 * Wrapper around fatal()
	 * 
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @param arg1
	 *            the throwable info to append
	 * @see org.apache.log4j.Category#fatal(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	public void fatal(Object arg0, Throwable arg1) {
		LOG.error(logger.getName() + ": " + arg0, arg1);
		// logger.fatal(arg0, arg1);
		if (arg0 != null)
			error(arg0, arg1);
	}

	/**
	 * Wrapper around info(), checks to see if info is enabled before logging
	 * the message
	 * 
	 * @param className
	 *            the class from which the message is logged
	 * @param methodName
	 *            the method from which the message is logged
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @param arg1
	 *            the throwable info to append
	 * @see org.apache.log4j.Category#info(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	public void info(String className, String methodName, Object arg0,
			Throwable arg1) {
		LOG.error(logger.getName() + ": " + arg0, arg1);
		if (isInfoEnabled() && arg0 != null) // logger.info(getMsgString(className,
												// methodName) + arg0, arg1);
			logger.log(Level.INFO, getMsgString(className, methodName) + arg0,
					arg1);
	}

	/**
	 * Wrapper around info(), checks to see if info is enabled before logging
	 * the message
	 * 
	 * @param className
	 *            the class from which the message is logged
	 * @param methodName
	 *            the method from which the message is logged
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @see org.apache.log4j.Category#info(java.lang.Object)
	 */
	public void info(String className, String methodName, Object arg0) {
		LOG.debug(logger.getName() + ": " + arg0);
		if (isInfoEnabled() && arg0 != null) // logger.info(getMsgString(className,
												// methodName) + arg0);

			logger.log(Level.INFO, getMsgString(className, methodName) + arg0);
	}

	/**
	 * Wrapper around info(), checks to see if info is enabled before logging
	 * the message
	 * 
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @see org.apache.log4j.Category#info(java.lang.Object)
	 */
	public void info(Object arg0) {
		LOG.debug(logger.getName() + ": " + arg0);
		if (isInfoEnabled() && arg0 != null) // logger.info(arg0);
			logger.info(arg0.toString());
	}

	/**
	 * Wrapper around info(), checks to see if info is enabled before logging
	 * the message
	 * 
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @param arg1
	 *            the throwable info to append
	 * @see org.apache.log4j.Category#info(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	public void info(Object arg0, Throwable arg1) {
		LOG.error(logger.getName() + ": " + arg0, arg1);
		if (isInfoEnabled() && arg0 != null) // logger.info(arg0, arg1);
			logger.log(Level.INFO, arg0.toString(), arg1);
	}

	/**
	 * Wrapper around warn()
	 * 
	 * @param className
	 *            the class from which the message is logged
	 * @param methodName
	 *            the method from which the message is logged
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @param arg1
	 *            the throwable info to append
	 * @see org.apache.log4j.Category#warn(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	public void warn(String className, String methodName, Object arg0,
			Throwable arg1) {
		LOG.warn(logger.getName() + ": " + arg0, arg1);
		// logger.warn(getMsgString(className, methodName) + arg0, arg1);
		if (arg0 != null)
			logger.log(Level.WARNING, getMsgString(className, methodName)
					+ arg0, arg1);
	}

	/**
	 * Wrapper around warn()
	 * 
	 * @param className
	 *            the class from which the message is logged
	 * @param methodName
	 *            the method from which the message is logged
	 * @param arg0
	 *            the object to log (usually a String)
	 * @see org.apache.log4j.Category#warn(java.lang.Object)
	 */
	public void warn(String className, String methodName, Object arg0) {
		LOG.warn(logger.getName() + ": " + arg0);
		// logger.warn(getMsgString(className, methodName) + arg0);
		if (arg0 != null)
			logger.log(Level.INFO, getMsgString(className, methodName) + arg0);
	}

	/**
	 * Wrapper around warn()
	 * 
	 * @param arg0
	 *            the object to log (usually a String)
	 * @see org.apache.log4j.Category#warn(java.lang.Object)
	 */
	public void warn(Object arg0) {
		LOG.warn(logger.getName() + ": " + arg0);
		// logger.warn(arg0);
		if (arg0 != null)
			logger.warning(arg0.toString());
	}

	/**
	 * Wrapper around warn()
	 * 
	 * @param arg0
	 *            the objet to log (usually a String)
	 * @param arg1
	 *            the throwable info to append
	 * @see org.apache.log4j.Category#warn(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	public void warn(Object arg0, Throwable arg1) {
		LOG.warn(logger.getName() + ": " + arg0, arg1);
		// logger.warn(arg0, arg1);
		if (arg0 != null)
			logger.log(Level.WARNING, arg0.toString(), arg1);
	}

	/**
	 * Wrapper around isDebugEnabled()
	 * 
	 * @see org.apache.log4j.Category#isDebugEnabled()
	 */
	public boolean isDebugEnabled() {
		return true;
		// return (logger.isLoggable(Level.FINEST));
	}

	/**
	 * Wrapper around isInfoEnabled()
	 * 
	 * @see org.apache.log4j.Category#isInfoEnabled()
	 */
	public boolean isInfoEnabled() {
		return true;
		// return (logger.isLoggable(Level.INFO));
	}

	/**
	 * @param name
	 *            the logger name
	 */
	private BAPLogger(String name) {
		logger = Logger.getLogger(name);
	}

	/**
	 * Utility method to concatenate the class and methtod name in a format
	 * suitable for logging
	 * 
	 * @param className
	 *            the name of the class
	 * @param methodName
	 *            the name of the method
	 * @return an appropriately concatenated String
	 */
	private String getMsgString(String className, String methodName) {

		return "";
	}

}
