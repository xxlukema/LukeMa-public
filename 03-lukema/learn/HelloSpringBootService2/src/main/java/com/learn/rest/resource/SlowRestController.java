package com.learn.rest.resource;


import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.pojo.Greeting;


@RequestMapping("/spring")
@RestController
public class SlowRestController {

    private static final Logger LOG = LogManager.getLogger();

    private static final String TEMPLATE = "Hello, %s!";
    private static final AtomicLong COUNTER = new AtomicLong();

    private static final long SLEEP_TIME_IN_SECONDS = 10_000;

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
    @GetMapping("/slowget")
    public Greeting slowget(@RequestParam(value = "name", defaultValue = "World") String name, @RequestAttribute("requestId") Long requestId) {

        LOG.info(() -> "GET: Called. name=" + name + "requestId=" + requestId);

        try {
            LOG.info(() -> "GET: Sleeping for " + SLEEP_TIME_IN_SECONDS / 1_000 + " seconds. Please wait...");

            Thread.sleep(SLEEP_TIME_IN_SECONDS);

            LOG.info(() -> "GET: After sleep.");
        } catch (Exception e) {
            LOG.error("GET: Exception while sleeping.", e);
        }

        return new Greeting(COUNTER.incrementAndGet(), String.format(TEMPLATE, name));
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
    @PostMapping(value = "/slowpost")
    public MyPojoObject slowpost(@RequestBody MyPojoObject myPojoObject, @RequestAttribute("requestId") Long requestId) {

        LOG.info(() -> "POST: Called. myPojoObject=" + myPojoObject + " requestId=" + requestId);

        try {
            LOG.info(() -> "POST: Sleeping for " + SLEEP_TIME_IN_SECONDS / 1_000 + " seconds. Please wait...");

            Thread.sleep(SLEEP_TIME_IN_SECONDS);

            LOG.info(() -> "POST: After sleep.");
        } catch (Exception e) {
            LOG.error("POST: Exception while sleeping.", e);
        }

        myPojoObject.setBody("Hello " + myPojoObject.getBody() + " " + COUNTER.incrementAndGet());

        return myPojoObject;
    }
}


class MyPojoObject {
    private String title;
    private String body;
    private Long userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return String.format("MyRequest [title=%s, body=%s, userId=%s]", title, body, userId);
    }

}
