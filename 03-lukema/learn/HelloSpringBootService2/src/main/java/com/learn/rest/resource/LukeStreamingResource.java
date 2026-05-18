package com.learn.rest.resource;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/spring")
@RestController
public class LukeStreamingResource {

    private static final Logger LOG = LogManager.getLogger();

    private List<String> LIST = new ArrayList<>();
    private static int COUNTER = 0;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/stream", produces = { MediaType.APPLICATION_JSON })
    public List<String> getStream()
        throws Exception {
        LOG.debug("Enter...");

        LIST.add("Hello client.");

        new Thread(() -> {

            while (COUNTER < 100) {
                LIST.add("Next: " + COUNTER++);

                LOG.info("New line added.");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    LOG.error("Interrupted.", e);
                }
            }
            
            COUNTER = 0;

        });
        
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            LOG.error("Interrupted.", e);
        }

        return LIST;
    }

}
