package com.learn.listener;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;


public class MyJobListener
    implements JobListener {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String getName() {
        return "My Jobs";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        //LOG.info("jobToBeExecuted(): " + context);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        LOG.info("jobExecutionVetoed(): " + context);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException exception) {
        /*
        LOG.info("jobWasExecuted(): " + context, exception);

        if (exception != null) {
            LOG.info(new Date() + ": Report generation error -- jobWasExecuted");
        } else {
            LOG.info(new Date() + ": Report generation No Exception -- jobWasExecuted");
        }
        */
    }
}
