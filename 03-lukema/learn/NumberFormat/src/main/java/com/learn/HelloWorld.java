package com.learn;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.log4j.Logger;

public class HelloWorld {
	private static final Logger LOG = Logger.getLogger(HelloWorld.class);

	private static final String PATTERN = "#,###;(#,###)";

	NumberFormat NF = new DecimalFormat("0000");

	public static void main(String[] args) {
		LOG.info("Hello World!");

		double f = -23156.3;

		LOG.info(f + ": " + new DecimalFormat(PATTERN).format(f));

	}
}
