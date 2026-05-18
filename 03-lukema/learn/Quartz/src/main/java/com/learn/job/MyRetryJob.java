package com.learn.job;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class MyRetryJob
    implements Job {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void execute(JobExecutionContext context)
        throws JobExecutionException {

        LOG.info("Hello MyRetryJob! ##########");
    }

}
