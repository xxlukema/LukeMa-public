package com.learn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class HelloTest {
	private static final Logger LOG = LogManager.getLogger();

	@Test
	public void runTest() throws Exception {
		LOG.info("Begin Test.");

		LOG.info("End Test.");

	}
}
