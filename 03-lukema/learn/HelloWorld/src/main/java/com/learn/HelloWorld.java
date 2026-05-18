package com.learn;

import org.apache.log4j.Logger;
import org.junit.Test;

public class HelloWorld {
    private static final Logger LOG = Logger.getLogger(HelloWorld.class);

	@Test
	public void test() throws Exception {
		LOG.info("Begin Test.");

		LOG.info("Hello World!");

		LOG.info("End Test.");
		
		
		

	}
}
