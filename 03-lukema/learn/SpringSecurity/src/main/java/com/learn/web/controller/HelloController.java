package com.learn.web.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HelloController {

    private static final Logger LOG = LogManager.getLogger();

    // @GetMapping(value = "test")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(ModelMap model) {

        LOG.debug("Enter.");
    }

    // @GetMapping(value = "403")
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public void accessDenied(ModelMap model) {

        LOG.debug("Enter.");

        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(item -> item.getAuthority()).forEach(LOG::info);
    }

    // @GetMapping(value = "/welcome")
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public void printWelcome(ModelMap model) {

        LOG.debug("Enter.");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        LOG.info("Logged in: username=" + username);

        model.addAttribute("username", username);
        model.addAttribute("message", "Spring Security Custom Form example");
    }

    /**
     * At create view. Before form post.
     */
    // @GetMapping(value = "/login")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(ModelMap model) {

        LOG.debug("Enter.");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName(); //get logged in username
        LOG.info("(At create view. Before form post. It will be 'anonymousUser'.) Before login: username=" + username);

        model.addAttribute("username", username);
    }

    // @GetMapping(value = "/loginfailed")
    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {

        LOG.debug("Enter.");

        model.addAttribute("error", "true");

        return "login";
    }

    @Autowired
    public void read() {
        LOG.info("========== Service is ready. ==========");
    }

    /*
    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
    
        LOG.debug("Enter.");
    
        session.invalidate();
    
        return "logout";
    }
    */

}
