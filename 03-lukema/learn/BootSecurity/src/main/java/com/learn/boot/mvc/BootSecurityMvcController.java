package com.learn.boot.mvc;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * The return view names should not start with '/'. View names should be Relative path.
 * 
 * change 
 * @Override 
 * public void addViewControllers(ViewControllerRegistry registry) {
 *     registry.addViewController("/login").setViewName("/login");
 *     registry.addViewController("/").setViewName("/login");
 * }
 * to 
 * @Override
 * public void addViewControllers(ViewControllerRegistry registry) {
 *     registry.addViewController("/login").setViewName("login");
 *     registry.addViewController("/").setViewName("login");
 * } 
 * 
 * The return view name should be Relative path
 *
 */
@Controller
public class BootSecurityMvcController {

    private static final Logger LOG = LogManager.getLogger();

    @GetMapping("/")
    public String root() {
        LOG.debug("Called");

        printUserDetails();

        /**
         * The return view name should be Relative path
         */
        return "home";
    }

    @GetMapping("/home")
    public void home() {
        LOG.debug("Called");

        printUserDetails();

        /**
         * "return null;" is same as a void method. They all return to the matching path.
         */
        return;
    }

    @GetMapping("/about")
    public String about() {
        LOG.debug("Called");

        /**
         * "return null;" is same as a void method. They all return to the matching path.
         */
        return null;
    }

    @GetMapping("/login")
    public void login() {
        LOG.debug("Called");
    }

    @GetMapping("/logout")
    public void logout() {
        LOG.debug("Called");
    }

    @GetMapping("/403")
    public String error403() {
        LOG.debug("Called");

        /**
         * The view name should be Relative path
         */
        return "error/403";
    }

    @RequestMapping("/404")
    public String notFound(Model model) {

        LOG.info("Called. " + model.asMap().get("username"));

        model.addAttribute("remoteUser", model.asMap().get("username"));

        return "error/404";
    }

    @GetMapping("/admin")
    public void admin() {
        LOG.debug("Called");
    }

    @GetMapping("/user")
    public void user() {
        LOG.debug("Called");
    }

    private void printUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOG.info("Authentication = " + auth.toString());
    }

    @Autowired
    @Order(20)
    public void ready1() {
        LOG.info("================= Servcer Started @Order(20) =================");
    }

    @Autowired
    @Order(10)
    public void ready2() {
        LOG.info("================= Servcer Started @Order(10) =================");
    }
}
