package com.learn.test;


import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.learn.job.PassParameterJob;


public class PassParameterJobTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testPassParameter()
        throws SchedulerException {

        LOG.info("Begin Test");

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        
        //create first JobDetail and Trigger
        JobDetail jobDetail = JobBuilder.newJob(PassParameterJob.class).withIdentity("ramjob1", "ourgroup").build();
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("ramtrigger1", "ourgroup").startAt(new Date(Calendar.getInstance().getTimeInMillis() + 5000))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).withRepeatCount(3)).build();
        
        //add passing parameters to JobDataMap for first JobDetail 
        jobDetail.getJobDataMap().put(PassParameterJob.NAME, "RAM");
        jobDetail.getJobDataMap().put(PassParameterJob.COUNT, 11);
        scheduler.scheduleJob(jobDetail, trigger);
        
        //create second JobDetail and Trigger       
        jobDetail = JobBuilder.newJob(PassParameterJob.class).withIdentity("ramjob2", "ourgroup").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("ramtrigger2", "ourgroup").startAt(new Date(Calendar.getInstance().getTimeInMillis() + 5000))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).withRepeatCount(3)).build();
        
        //add passing parameters to JobDataMap for second JobDetail         
        jobDetail.getJobDataMap().put(PassParameterJob.NAME, "RAHIM---2");
        jobDetail.getJobDataMap().put(PassParameterJob.COUNT, 21);
        scheduler.scheduleJob(jobDetail, trigger);
        
        scheduler.start();
        
        try {
            //wait for 30 seconds to finish the job
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            LOG.error("InterruptedException", e);
        }
        
        //shutdown scheduler gracefully
        scheduler.shutdown(true);

        LOG.info("End Test.");
    }

}
