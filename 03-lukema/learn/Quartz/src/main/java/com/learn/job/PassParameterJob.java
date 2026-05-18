package com.learn.job;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;


@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class PassParameterJob
    implements Job {

    private static final Logger LOG = LogManager.getLogger();

    public static final String NAME = "name";
    public static final String COUNT = "count";

    @Override
    public void execute(JobExecutionContext context)
        throws JobExecutionException {

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        //fetch parameters from JobDataMap
        String name = dataMap.getString(NAME);
        int count = dataMap.getInt(COUNT);

        JobKey jobKey = context.getJobDetail().getKey();
        LOG.info(jobKey + ": name=" + name + ", counter=" + count);

        count++;
        //add next counter to JobDataMap
        dataMap.put(COUNT, count);
    }
}
