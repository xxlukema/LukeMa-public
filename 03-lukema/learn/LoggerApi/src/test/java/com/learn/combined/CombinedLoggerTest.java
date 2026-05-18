package com.learn.combined;

import org.junit.Test;

import com.learn.jul.util.MyJulLogConfigReader;

public class CombinedLoggerTest {

	private static final org.apache.logging.log4j.Logger LOG4J2 = org.apache.logging.log4j.LogManager
			.getLogger(CombinedLoggerTest.class);
	private static final org.apache.log4j.Logger LOG4J = org.apache.log4j.Logger.getLogger(CombinedLoggerTest.class);
	private static final java.util.logging.Logger LOGGER_JUL = java.util.logging.Logger
			.getLogger(CombinedLoggerTest.class.getName());

	@Test
	public void testLoggers() {

		LOG4J2.info("Begin Test.");

		LOG4J2.debug("log4j2 debug Called.");
		LOG4J2.info("log4j2 info Called.");
		LOG4J.debug("log4j debug Called.");
		LOG4J.info("log4j info Called.");

		// -Djava.util.logging.config.file=/tmp/logging.properties
		MyJulLogConfigReader.readConfig();

		LOGGER_JUL.finest("JUL finest Called.");
		LOGGER_JUL.finer("JUL finer Called.");
		LOGGER_JUL.fine("JUL fine Called.");
		LOGGER_JUL.info("JUL info Called.");
		LOGGER_JUL.severe("JUL severe Called.");

		LOG4J2.info("End Test.");

	}

}
