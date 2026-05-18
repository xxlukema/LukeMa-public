package com.learn.bean;


import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//@Named("myNamedBean")
public class MyNamedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LogManager.getLogger();

	public void sayHello() {
		LOG.info("Hello");
	}

}
