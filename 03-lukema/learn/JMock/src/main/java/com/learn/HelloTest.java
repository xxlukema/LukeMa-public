package com.learn;

import org.apache.log4j.Logger;
import org.junit.Test;

public class HelloTest {
	protected static final Logger LOG = Logger.getLogger(HelloTest.class);

	@Test
	public void runTest() throws Exception {
		LOG.info("Hello World! 2");

	}
}
