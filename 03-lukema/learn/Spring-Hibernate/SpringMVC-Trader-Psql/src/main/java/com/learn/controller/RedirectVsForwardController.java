package com.learn.controller;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class RedirectVsForwardController {

    private static final Logger LOG = LogManager.getLogger();

    @RequestMapping(value = "/Forward.go")
    public String doForward() {

        LOG.debug("Doing forward.");

        return "forward:RedirectVsForward2";
    }

    @RequestMapping(value = "/Redirect.go")
    public String doRedrect() {

        LOG.debug("Doing redirect.");

        return "redirect:RedirectVsForward2";
    }

}
