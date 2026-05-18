package com.learn.eureka.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
public class SchoolServiceController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/getSchoolDetails/{schoolname}", method = RequestMethod.GET)
    public String getStudents(@PathVariable String schoolname) {
        log.info("Getting School details for {}", () -> schoolname);
        String response = restTemplate.exchange("http://student-service/getStudentDetailsForSchool/{schoolname}", HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                }, schoolname).getBody();

        log.info("Response Received as {}", () -> response);

        return "School Name -  " + schoolname + " \n Student Details " + response;
    }

}
