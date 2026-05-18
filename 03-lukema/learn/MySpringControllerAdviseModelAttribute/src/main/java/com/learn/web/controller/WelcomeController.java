package com.learn.web.controller;


import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class WelcomeController {

    private static final Logger LOG = LogManager.getLogger();
    
    // inject via application.properties
    @Value("${welcome.message:test}")
    private String message = "Hello World";

    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        
        LOG.debug("Enter.");
        
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome(Map<String, Object> model) {
        
        model.put("message", this.message);
        
        return "welcome";
    }

}
