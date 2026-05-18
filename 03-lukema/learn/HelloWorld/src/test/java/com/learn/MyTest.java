package com.learn;

import org.junit.Test;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MyTest {

	@Test
	public void testOne() {

		log.debug(() -> "test");

	}

}
