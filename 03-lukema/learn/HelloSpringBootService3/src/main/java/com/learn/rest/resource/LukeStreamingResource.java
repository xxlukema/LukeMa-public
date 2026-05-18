package com.learn.rest.resource;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.ws.rs.core.MediaType;

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

    private static final Logger log = LogManager.getLogger();

    private final List<String> LIST = new ArrayList<>();
    private static final AtomicInteger ATOMIC_COUNTER = new AtomicInteger(0);

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/stream", produces = { MediaType.APPLICATION_JSON })
    public List<String> getStream() {
        LIST.add("Hello client.");
        
        try (ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();) {
            executor.scheduleAtFixedRate(() -> {
                if (ATOMIC_COUNTER.get() < 100) {
                    LIST.add("Next: " + ATOMIC_COUNTER.incrementAndGet());
                    log.debug(() -> "New line added.");
                } else {
                    ATOMIC_COUNTER.set(0);
                    executor.shutdown();
                }
            }, 0, 500, TimeUnit.MILLISECONDS);

            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                log.error("Interrupted.", e);
            }
        }

        return LIST;
    }

}
