package com.learn;

import java.util.Arrays;
import java.util.List;

public class LogArrayList {
	protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(LogArrayList.class);

	public static void main(String[] args) throws Exception {
		String[] array = { "Line one", "Line 2", "Line three", };

		LOG.debug("Array: " + array);

		List<String> list = Arrays.asList(array);

		LOG.debug("List: " + list);
	}
}
