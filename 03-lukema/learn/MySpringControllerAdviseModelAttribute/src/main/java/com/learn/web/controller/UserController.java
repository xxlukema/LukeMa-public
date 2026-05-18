package com.learn.web.controller;


import java.io.FileNotFoundException;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learn.web.model.User;


@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LogManager.getLogger();

    @GetMapping(value = "signup")
    public ModelAndView signup() {

        LOG.debug("Enter.");

        return new ModelAndView("/user/signup", "user", new User());
    }

    @PostMapping(value = "createUser")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes)
        throws FileNotFoundException {

        LOG.debug("Enter. user = " + user);

        if (user.getName().equals("exception")) {
            throw new FileNotFoundException("Error found.");
        }

        if (result.hasErrors()) {

            LOG.error(result.getAllErrors());

            return "/user/signup";
        }

        redirectAttributes.addFlashAttribute("message", "User successfully created. If you this text, you come to this page from create user page.");

        /**
         * returns a redirect view name. There must be a riew resolver like: @GetMapping(value = "success")
         */
        return "redirect:/user/success";
    }

    /**
     * If a controller returns a null view name, or declares a void return type, Spring will attempt to infer the view name 
     * from the request URL.
     * 
     * It does this using an implementation of RequestToViewNameTranslator, the default implementation of which is 
     * DefaultRequestToViewNameTranslator
     * 
     * RequestToViewNameTranslator that simply transforms the URI of the incoming request into a view name.
     * 
     * The default transformation simply strips leading and trailing slashes as well as the file extension of the URI, and returns 
     * the result as the view name with the configured "prefix" and a "suffix" added as appropriate.
     * 
     * The stripping of the leading slash and file extension can be disabled using the "stripLeadingSlash" and "stripExtension" 
     * properties, respectively.
     * 
     */
    @GetMapping(value = "success")
    public String success() {

        LOG.debug("Enter.");

        // return "/user/success"; 

        /**
         * "return null as string view name" or "void @RequestMapping function" infers the view name from the request URL.
         * 
         * So "return null as string view name" or "void @RequestMapping function" is equivalent to return "/user/success"; 
         * 
         */
        return null;
    }

}
