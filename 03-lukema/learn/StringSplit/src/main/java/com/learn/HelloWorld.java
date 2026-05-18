package com.learn;

import org.apache.log4j.Logger;

public class HelloWorld {
	private static final Logger LOG = Logger.getLogger(HelloWorld.class);

	public static void main(String[] args) {
		String str = "";
		String[] sub = str.split("-");
		LOG.info("########### sub.length: " + sub.length);
		for (String st : sub) {
			LOG.info("st: " + st);
		}

		str = "-";
		sub = str.split("-");
		LOG.info("########### sub.length: " + sub.length);
		for (String st : sub) {
			LOG.info("st: " + st);
		}

		str = "AA-aba- -- aa";
		sub = str.split("-");
		LOG.info("########### sub.length: " + sub.length);
		for (String st : sub) {
			LOG.info("st: #" + st + "#");
		}

	}
}
