package com.learn.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MyJob implements Job {

	private static final Logger LOG = LogManager.getLogger();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		LOG.info("Hello MyJob!");

		// fireRetryJob(context);

		fireMyOtherJob(context);
	}

	private void fireMyOtherJob(JobExecutionContext context) throws JobExecutionException {
		JobKey jobKey = new JobKey("MyOtherJob", "put");
		Scheduler scheduler = null;
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		if (schedulerFactory != null) {
			try {
				scheduler = schedulerFactory.getScheduler();
				scheduler.triggerJob(jobKey);
			} catch (SchedulerException e) {
				LOG.error("SchedulerException", e);
			}
		}
	}

	public void fireRetryJob(JobExecutionContext context) throws JobExecutionException {
		JobDetail job = JobBuilder.newJob(MyRetryJob.class).withIdentity("dummyJobName", "group1").build();

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "group1")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).withRepeatCount(3))
				.build();

		Scheduler scheduler = null;
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		if (schedulerFactory != null) {
			try {
				scheduler = schedulerFactory.getScheduler();
				scheduler.scheduleJob(job, trigger);
			} catch (SchedulerException e) {
				LOG.error("SchedulerException", e);
			}
		}

	}

}
