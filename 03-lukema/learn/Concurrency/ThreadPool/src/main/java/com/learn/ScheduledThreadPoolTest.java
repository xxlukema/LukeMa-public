package com.learn;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ScheduledThreadPoolTest {
	protected static final Logger LOG = Logger
			.getLogger(ScheduledThreadPoolTest.class);

	private static ScheduledExecutorService scheduledExecutorService = Executors
			.newScheduledThreadPool(2, new MyThreadFactory());

	public static void main(String[] args) throws Exception {
		LOG.info("Hello World!");

		MyThread myThread = new MyThread();
		MyOtherThread myOtherThread = new MyOtherThread();
		MyPrintThread myPrintThread = new MyPrintThread();

		scheduledExecutorService.scheduleWithFixedDelay(myPrintThread, 0, 1L,
				TimeUnit.SECONDS);
		scheduledExecutorService.schedule(myThread, 5L, TimeUnit.SECONDS);
		scheduledExecutorService.schedule(myOtherThread, 5L, TimeUnit.SECONDS);
		scheduledExecutorService.schedule(myThread, 5L, TimeUnit.SECONDS);
		scheduledExecutorService.schedule(myOtherThread, 5L, TimeUnit.SECONDS);

		try {
			Thread.sleep(20000);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		scheduledExecutorService.shutdown();

		scheduledExecutorService.awaitTermination(10L, TimeUnit.SECONDS);
	}

}
