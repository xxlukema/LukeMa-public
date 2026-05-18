package com.learn.jul.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.slf4j.bridge.SLF4JBridgeHandler;

public class JulConfigReader {

	// -Djava.util.logging.config.file=/tmp/logging.properties
	// -Dlog4j.configuration=file://${com.sun.aas.instanceRoot}/config/log4j.properties
	// -Dlog4j.configurationFile=file://${com.sun.aas.instanceRoot}/config/log4j2.xml
	public static final String LogConfigFileName = "/logging.properties";

	public static void readConfig() {

		LogManager.getLogManager().reset();
		SLF4JBridgeHandler.removeHandlersForRootLogger();

		System.out.println("Loading Logger Configuration...");
		try (InputStream inputStream = JulConfigReader.class.getResourceAsStream(LogConfigFileName)) {
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("Loaded Logger Configuration.");

		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		Logger.getLogger("global").setLevel(Level.FINEST);
	}

}
