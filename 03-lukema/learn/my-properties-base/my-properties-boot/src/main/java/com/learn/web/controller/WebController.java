package com.learn.web.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebController {

    // inject via application.properties
    @Value("${welcome.message:test}")
    private final String message = "Hello World";

    @GetMapping("/")
    public String index(Map<String, Object> model) {
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome(Map<String, Object> model) {
        model.put("message", this.message);
        return "welcome";
    }

    @GetMapping("/ProertyReport")
    public String proertyReport(Map<String, Object> model) {
        return "ProertyReport";
    }

}
