package com.learn;

import org.apache.log4j.Logger;

public class MyThreadBase extends Thread {
	private static final Logger LOG = Logger.getLogger(MyThreadBase.class);

	public void run() {
		for (int i = 0; i < 4; i++) {
			/*LOG.info(Thread.currentThread().getName()
					+ " MyThreadLocal.generateId(): "
					+ MyThreadLocal.generateId());*/

			LOG.info(Thread.currentThread().getName()
					+ " MyThreadLocal.getPojothreadlocal(): "
					+ MyThreadLocal.getPojothreadlocal().get().getId());
		}

	}
}
