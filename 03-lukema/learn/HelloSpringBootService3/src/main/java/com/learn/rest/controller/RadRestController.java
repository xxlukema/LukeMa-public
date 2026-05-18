package com.learn.rest.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/rad")
@RestController
public class RadRestController {

    private static final Logger log = LogManager.getLogger();

    @GetMapping("/ping")
    public String ping() {

        log.debug(() -> "Called. ping()");

        return "OK!\n";
    }

    @GetMapping("/props")
    public String props() {

        log.debug(() -> "Called. props()");

        return "{}";
    }

    @GetMapping("/representative/generateLink")
    public String generateLink(@RequestParam(defaultValue = "sales@aol.com") String email) {

        log.debug("Called. generateLink(). email= {}", () -> email);

        return "{}";
    }

    /**
     * Difference between @RequestParam and @RequestAttribute
     *
     * @RequestParam is used to bind parameter values from 'query string' e.g. in http://www.example.com?myParam=3,
     * myParam=3 can populate @RequestParam parameter.
     *
     * On the other hand, @RequestAttribute is to access objects which have been populated on the server-side but during the
     * same HTTP request, for example they can be populated in an interceptor or a filter.
     *
     * requestId is set by BootWebFilter.
     */
    /**
    @PostMapping(value = "/post/ping")
    public String postPing(@RequestBody MyPojo myPojo, @RequestAttribute("requestId") Long requestId) {

        log.debug(() -> "Called. myPojo=" + myPojo);
        log.debug(() -> "requestId=" + requestId);

        return "POST OK!\n";
    }
    */
}
