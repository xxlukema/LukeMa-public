package com.learn.bbb;

import java.util.Date;

import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

@Named
public class StringFormat {
	private static final Logger LOG = LogManager.getLogger();

	String str1 = "String One";
	String str2 = "String Two";
	String str3 = "String Three";
	String pattern = "%s %s %s %s %s %s";
	int i = 12345;
	Float f = 2.34455F;
	Date date = new Date();

	private final int Counter = 1_000_000;

	@Test
	public void runFormat() throws Exception {
		LOG.info("Begin Test");

		long start = System.currentTimeMillis();

		for (int k = 0; k < Counter; k++) {
			@SuppressWarnings("unused")
			String str = String.format(pattern, str1, str2, str3, i, f, date);
		}

		long end = System.currentTimeMillis();

		LOG.info(end - start);

		LOG.info("End Test.");

	}

	@Test
	public void runPlus() throws Exception {
		LOG.info("Begin Test");

		long start = System.currentTimeMillis();

		for (int k = 0; k < Counter; k++) {
			@SuppressWarnings("unused")
			String str = str1 + " " + str2 + " " + str3 + " " + i + " " + f + " " + date;
		}

		long end = System.currentTimeMillis();

		LOG.info(end - start);

		LOG.info("End Test.");

	}
}
