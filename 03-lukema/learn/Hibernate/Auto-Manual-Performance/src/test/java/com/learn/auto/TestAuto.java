package com.learn.auto;

import junit.framework.TestCase;

public class TestAuto extends TestCase {
	public void testAddRecord() throws Exception {
		for (int i = 0; i < 4000; i++) {
			AutoDriver.addRecord();
		}
	}

	public void testRetrieveData() throws Exception {
		AutoDriver.retrieveData();
	}

	public void testSelectForAutoChildOneChild() throws Exception {
		AutoDriver.selectForAutoChildOneChild();
	}

	public void testSelectAutoChildTwo() throws Exception {
		AutoDriver.selectAutoChildTwo();
	}

	public void testSelectAutoChildOne() throws Exception {
		AutoDriver.selectAutoChildOne();
	}

	public void testSelectForAutoChildOneChild_Criteria() throws Exception {
		AutoDriver.selectForAutoChildOneChild_Criteria();
	}

	public void testSelectAutoParent() throws Exception {
		AutoDriver.selectAutoParent();
	}
	
	public void testSelectAutoParent_Criteria() throws Exception {
		AutoDriver.selectAutoParent_Criteria();
	}

}
