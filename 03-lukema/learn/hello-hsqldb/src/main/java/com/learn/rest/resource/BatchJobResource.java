package com.learn.rest.resource;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/spring/batch")
@RestController
public class BatchJobResource {

    private static final Logger log = LogManager.getLogger();

    // @Autowired
    // private JobLauncher jobLauncher;

    // @Autowired
    // private Job job;

    // @Scheduled(fixedDelay = 3_000L)
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "ping", produces = { MediaType.TEXT_PLAIN_VALUE })
    public String selectCurrentDate()
        throws Exception {
        log.debug(() -> "Enter...");

        // jobLauncher.run(job, new JobParameters());

        try {
            return "Job triggered";
        } finally {
            log.debug(() -> "Leave.");
        }
    }

}
