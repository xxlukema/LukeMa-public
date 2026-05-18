package com.learn.jul;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.junit.Ignore;
import org.junit.Test;

import com.learn.jul.util.LoggingApi;
import com.learn.jul.util.MyJulLogConfigReader;
import com.learn.jul.util.MyLogFormatter;

@SuppressWarnings("deprecation")
public class LoggingApiTest {

	private static final Logger LOG = Logger.getLogger(LoggingApiTest.class.getName());

	@Test
	public void testLogCustomized() throws Exception {

		MyJulLogConfigReader.readConfig();

		LoggingApi.doLog(LOG);
	}

	@Ignore
	@Test
	public void testLogDefault() throws Exception {

		LoggingApi.doLog(LOG);
	}

	@Ignore
	@Test
	public void testLogDuplicated() throws Exception {

		MyJulLogConfigReader.readConfig();

		Handler handle0 = new ConsoleHandler();
		LOG.addHandler(handle0);
		Formatter formatter0 = new MyLogFormatter();
		handle0.setFormatter(formatter0);

		Handler handle1 = new FileHandler();
		LOG.addHandler(handle1);
		Formatter formatter1 = new MyLogFormatter();
		handle1.setFormatter(formatter1);

		Handler handle2 = new FileHandler("target/logHandler%g.txt", 20000, 3, false);
		Formatter formatter2 = new SimpleFormatter();
		LOG.addHandler(handle2);
		handle2.setFormatter(formatter2);

		LoggingApi.doLog(LOG);
	}

}
