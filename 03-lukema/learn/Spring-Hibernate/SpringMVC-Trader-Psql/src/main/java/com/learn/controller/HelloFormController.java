package com.learn.controller;


import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learn.command.Hello;
import com.learn.service.AppException;


@Controller
public class HelloFormController {
    private static final Logger LOG = LogManager.getLogger();

    @RequestMapping(value = "/HelloInput", method = RequestMethod.GET)
    public ModelAndView helloInput()
        throws AppException {

        LOG.debug("Inside helloInput()...");

        Hello hello = new Hello();
        hello.setFirstName("Luke");
        hello.setLastName("Ma");
        hello.setAge(20);

        ModelAndView modelAndView = new ModelAndView("HelloInput", "hello", hello);

        Map<String, Object> model = new HashMap<>();
        model.put("greetings", "Hello User for Input Page");
        modelAndView.addAllObjects(model);

        return modelAndView;
    }

    @RequestMapping(value = "/HelloSubmit", method = RequestMethod.POST)
    public ModelAndView helloSubmit(@ModelAttribute("command") Hello hello, ModelAndView modelAndView, RedirectAttributes redirectAttributes)
        throws AppException {

        LOG.debug("Inside helloSubmit()...");

        LOG.debug("firstName = " + hello.getFirstName());
        LOG.debug("lastName = " + hello.getLastName());
        LOG.debug("age = " + hello.getAge());

        modelAndView.setViewName("redirect:HelloResult.go");

        redirectAttributes.addFlashAttribute("greetings", "Hello User for Output Page");

        redirectAttributes.addFlashAttribute("command", hello);

        return modelAndView;
    }

    @RequestMapping(value = "/HelloResult", method = RequestMethod.GET)
    public String helloResult(Model model, @ModelAttribute("command") Hello hello)
        throws AppException {

        LOG.debug("Inside helloResult()...");

        LOG.debug("firstName = " + hello.getFirstName());
        LOG.debug("lastName = " + hello.getLastName());
        LOG.debug("age = " + hello.getAge());

        if (hello != null && hello.getLastName() != null) {
            hello.setLastName(hello.getLastName().toUpperCase());
        }

        return "HelloOutput";
    }

}
