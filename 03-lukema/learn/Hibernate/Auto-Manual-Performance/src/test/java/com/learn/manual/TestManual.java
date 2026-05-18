package com.learn.manual;

import junit.framework.TestCase;

public class TestManual extends TestCase {
	public void testAddRecord() throws Exception {
		for(int i=0; i<10000; i++)
		{
			ManualDriver.addRecord();
		}
	}

	public void testRetrieveData() throws Exception {
		ManualDriver.retrieveData();
	}

}
