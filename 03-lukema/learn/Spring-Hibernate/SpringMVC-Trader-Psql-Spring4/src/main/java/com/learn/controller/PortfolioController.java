package com.learn.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.learn.bean.Customer;
import com.learn.bean.PortfolioItem;
import com.learn.service.AppException;
import com.learn.service.CustomerService;
import com.learn.service.TradeService;
import com.learn.session.User;


@Transactional
@Controller
public class PortfolioController {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    CustomerService customerService;

    @Autowired
    TradeService tradeService;

    @Autowired
    private User user;

    @ModelAttribute("model")
    public Map<String, Object> populateModel() {

        LOG.debug("Inside populateModel()...");

        Customer customer = null;

        try {
            LOG.info("Call getCustomer.");
            customer = customerService.getCustomer(user.getUsername());
            LOG.info("Success with call getCustomer.");
        } catch (AppException ae) {
            LOG.info("Exception with call getCustomer.");
        }

        LOG.info("Calling customer.getPortfolio().getCash()...");
        Float cash = customer.getPortfolio().getCash();
        LOG.info("Success with customer.getPortfolio().getCash(): " + cash);

        List<PortfolioItem> portfolioItems = tradeService.getInitializedPortfolioItems(customer);

        LOG.info("portfolioItems.size(): " + portfolioItems.size());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("cash", cash);
        model.put("portfolioItems", portfolioItems);

        return model;
    }

    @RequestMapping(value = "/Portfolio", method = RequestMethod.GET)
    public ModelAndView handleRequest(@ModelAttribute("model") Map<String, Object> model) {
        LOG.info("Entering function.");

        return new ModelAndView("Portfolio", "command", model);
    }

}
