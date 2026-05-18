package com.learn.boot.mvc;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AngularJSMvcController {

    private static final Logger LOG = LogManager.getLogger();

    @GetMapping("/AngularJS/directive")
    public String directive() {

        LOG.debug("Called");

        return "AngularJS/directive";
    }
    
    @GetMapping("/AngularJS/filter")
    public String filter() {

        LOG.debug("Called");

        return "AngularJS/filter";
    }

}
