package com.learn.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * JUL configuration file loader.
 *
 */
public class JulConfigReader {

	public static final String LogConfigFileName = "/jul-logging.properties";

	/**
	 * Load JUL configuration file
	 */
	public static void readConfig() {

		System.out.println("Loading Logger Configuration...");
		try (InputStream inputStream = JulConfigReader.class.getResourceAsStream(LogConfigFileName)) {
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("Loaded Logger Configuration.");

		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

	}

}
