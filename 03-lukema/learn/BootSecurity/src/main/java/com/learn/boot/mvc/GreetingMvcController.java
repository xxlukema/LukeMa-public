package com.learn.boot.mvc;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GreetingMvcController {

    private static final Logger LOG = LogManager.getLogger();

    @GetMapping("/greeting")
    public void greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {

        LOG.info("Called.");

        model.addAttribute("name", name);
    }

    @GetMapping("/test")
    public void test() {
        LOG.debug("Called");
    }

}
