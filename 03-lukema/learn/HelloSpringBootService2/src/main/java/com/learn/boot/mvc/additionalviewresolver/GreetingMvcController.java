package com.learn.boot.mvc.additionalviewresolver;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;


/**
 * This can be moved to WebMvcConfig
 */
@Log4j2
@Controller
public class GreetingMvcController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {

        log.info(() -> "Called.");

        model.addAttribute("name", name);

        /**
         * The view name should be Relative path
         */
        return "greeting";
    }

}
