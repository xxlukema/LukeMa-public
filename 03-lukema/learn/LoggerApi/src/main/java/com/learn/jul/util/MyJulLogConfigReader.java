package com.learn.jul.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class MyJulLogConfigReader {

	// -Djava.util.logging.config.file=/tmp/logging.properties
	// -Dlog4j.configuration=file://${com.sun.aas.instanceRoot}/config/log4j.properties
	// -Dlog4j.configurationFile=file://${com.sun.aas.instanceRoot}/config/log4j2.xml
	public static final String LogConfigFileName = "/logging.properties";

	public static void readConfig() {

		System.out.println("++++++ Loading Logger Configuration...");
		try (InputStream inputStream = JulConfigReader.class.getResourceAsStream(LogConfigFileName)) {
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("Loaded Logger Configuration.");
	}

}
