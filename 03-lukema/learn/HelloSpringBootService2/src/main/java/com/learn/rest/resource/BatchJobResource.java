package com.learn.rest.resource;


import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/spring/batch")
@RestController
public class BatchJobResource {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    // @Scheduled(fixedDelay = 3_000L)
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "ping", produces = { MediaType.TEXT_PLAIN })
    public String selectCurrentDate()
        throws Exception {
        LOG.debug("Enter...");

        jobLauncher.run(job, new JobParameters());

        try {
            return "Job triggered";
        } finally {
            LOG.info("Leave.");
        }
    }

}
