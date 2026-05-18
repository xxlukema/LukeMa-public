package com.learn.boot.mvc.additionalviewresolver;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;


/**
 * This can be moved to WebMvcConfig
 */
@Log4j2
@Controller
public class SpringMvcController {

    @GetMapping("/404")
    public String notFound(Model model) {

        log.info(() -> "Called. " + model.asMap().get("username"));

        model.addAttribute("remoteUser", model.asMap().get("username"));

        /**
         * The view name should be Relative path
         */
        return "404";
    }

}
