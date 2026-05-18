package com.learn.clob;

import com.learn.clob.ObjectWithTextDriver;

import junit.framework.TestCase;

public class TestText extends TestCase {
	public void testAddRecord()
      throws Exception {
		for(int i=0; i<2; i++)
		{
			ObjectWithTextDriver.addRecord();
		}
	}

	public void testRetrieveData() {
		ObjectWithTextDriver.retrieveData();
	}

}
