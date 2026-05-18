package com.learn.test;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;


public class MyQuartzPropertyTest {

    private static final Logger LOG = Logger.getLogger(MyQuartzPropertyTest.class);

    @Test
    public void testSimpleJob()
        throws InterruptedException {

        LOG.info("Begin Test");

        //schedule it
        SchedulerFactory factory = new StdSchedulerFactory();

        try {
            Scheduler scheduler = factory.getScheduler();

            scheduler.start();

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
