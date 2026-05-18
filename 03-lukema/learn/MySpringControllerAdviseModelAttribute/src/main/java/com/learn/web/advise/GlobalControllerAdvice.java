package com.learn.web.advise;


import java.io.FileNotFoundException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice(basePackages = { "com.learn.web.controller" })
public class GlobalControllerAdvice {

    private static final Logger LOG = LogManager.getLogger();

    @ModelAttribute
    public void globalAttributes(Model model) {

        LOG.info("Adding advice_msg for all matching controller requests.");

        model.addAttribute("advice_msg", "Global welcome message from @ControllerAdvice for all requests!");
        model.addAttribute("today", new Date());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ModelAndView myError(Exception exception) {

        LOG.info("Handle FileNotFoundException exception.");

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.setViewName("/user/error");
        return mav;
    }
}
