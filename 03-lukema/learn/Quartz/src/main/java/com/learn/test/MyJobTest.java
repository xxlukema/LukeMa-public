package com.learn.test;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import com.learn.job.MyJob;


public class MyJobTest {

    private static final Logger LOG = Logger.getLogger(MyJobTest.class);

    private TriggerKey triggerKey = TriggerKey.triggerKey("myTrigger", "myTriggerGroup");

    @Test
    public void testSimpleJob()
        throws InterruptedException {

        LOG.info("Begin Test");

        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("myJob").build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(20).withRepeatCount(2))
                .startAt(DateBuilder.futureDate(0, DateBuilder.IntervalUnit.MINUTE)).build();

        //schedule it
        SchedulerFactory factory = new StdSchedulerFactory();

        try {
            Scheduler scheduler = factory.getScheduler();

            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);

            for (String groupName : scheduler.getJobGroupNames()) {

                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();

                    //get job's trigger
                    @SuppressWarnings("unchecked")
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();

                    LOG.info("[jobName] : " + jobName + ", [groupName] : " + jobGroup + " - " + nextFireTime);
                }
            }

            Thread.sleep(2 * 60_000);

            LOG.info("Shutdown.");

            scheduler.shutdown(true);

        } catch (SchedulerException | InterruptedException e) {
            LOG.error("Exception", e);
        }

        LOG.info("End Test.");
    }
}
