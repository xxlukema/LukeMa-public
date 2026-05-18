package com.learn.controller;


import net.neurotech.quotes.Quote;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learn.bean.Customer;
import com.learn.command.Trade;
import com.learn.service.AppException;
import com.learn.service.CustomerService;
import com.learn.service.TradeService;
import com.learn.session.User;
import com.learn.util.JavaFinancialLibraryUtils;


@Transactional
@Controller
public class TradeFormController {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    CustomerService customerService;

    @Autowired
    TradeService tradeService;

    @Autowired
    private User user;

    @ModelAttribute("trade")
    protected Trade populateTrade() {
        LOG.info("Entering function.");

        Trade trade = new Trade();
        trade.setBuySell(Trade.SELL);
        return trade;
    }

    @RequestMapping(value = "/Trade", method = RequestMethod.GET)
    public ModelAndView onBind(@ModelAttribute("trade") Trade trade) {
        LOG.info("Entering function.");

        if (JavaFinancialLibraryUtils.symbolIsValid(trade.getSymbol())) {
            Quote quote = JavaFinancialLibraryUtils.getQuote(trade.getSymbol());
            trade.setPrice(quote.getValue());
            trade.setSymbol(trade.getSymbol().toUpperCase());
        } else {
            //  errors.rejectValue("symbol", "error.trade.invalid-symbol", new Object[] { trade.getSymbol() }, "Invalid ticker symbol.");
        }

        return new ModelAndView("Trade", "command", trade);
    }

    /*protected void validatePage(Object command, Errors errors, int page) {
        LOG.info("Entering function.");

        LOG.info("Page number: " + page);

        Trade trade = (Trade) command;

        Customer customer = null;

        try {
            customer = customerService.getCustomer(user.getUsername());
        } catch (AppException ae) {
            errors.reject("error.trade.exception", ae.getMessage());
        }

        if (tradeService.tradeIsBuy(trade)) {
            if (tradeService.insufficientFunds(customer, trade)) {
                errors.reject("error.trade.insufficient-funds", "Insufficient funds.");
            }
        } else {
            if (!tradeService.ownStock(customer, trade.getSymbol())) {
                errors.rejectValue("symbol", "error.trade.dont-own", "You don't own this stock.");
            } else if (tradeService.notEnoughShares(customer, trade)) {
                errors.rejectValue("quantity", "error.trade.not-enough-shares", "Not enough shares.");
            }
        }
    }*/

    //@RequestMapping(value = "/Trade", method = RequestMethod.POST)
    public String processFinish_OK(@ModelAttribute("trade") Trade trade, BindingResult result, RedirectAttributes redirectAttributes) {
        LOG.info("Entering function.");

        LOG.info("################### Sleep for 5 seconds...");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOG.error("Thread Interrupted.", e);
        }

        Customer customer = null;

        try {
            customer = customerService.getCustomer(user.getUsername());
            if (tradeService.tradeIsBuy(trade)) {
                tradeService.buyStock(customer, trade.getSymbol(), trade.getShares(), trade.getPrice());
            } else {
                tradeService.sellStock(customer, trade.getSymbol(), trade.getShares(), trade.getPrice());
            }

            //return new ModelAndView("TradeAcknowledge", "trade", trade);
            //return new ModelAndView(new RedirectView("TradeAcknowledgeAction.go", true), "trade", trade);

            redirectAttributes.addFlashAttribute("trade", trade);
            return "redirect:TradeAcknowledgeAction.go";
        } catch (AppException ae) {
            LOG.error("Exception with data transaction: " + ae.getMessage());

            trade.setException(ae.getMessage());

            redirectAttributes.addFlashAttribute("command", trade);
            return "redirect:TradeException";
            //return new ModelAndView("TradeException", "command", trade);
        }
    }

    //@RequestMapping(value = "/Trade", method = RequestMethod.POST)
    public String processFinish_Good_New(@ModelAttribute("trade") Trade trade, BindingResult result, RedirectAttributes redirectAttributes) {
        LOG.info("Entering function.");

        LOG.info("################### Sleep for 5 seconds...");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOG.error("Thread Interrupted.", e);
        }

        Customer customer = null;

        try {
            customer = customerService.getCustomer(user.getUsername());
            if (tradeService.tradeIsBuy(trade)) {
                tradeService.buyStock(customer, trade.getSymbol(), trade.getShares(), trade.getPrice());
            } else {
                tradeService.sellStock(customer, trade.getSymbol(), trade.getShares(), trade.getPrice());
            }

            redirectAttributes.addFlashAttribute("trade", trade);
            return "redirect:/TradeAcknowledgeIntermediate.go";
        } catch (AppException ae) {
            LOG.error("Exception with data transaction: " + ae.getMessage());

            trade.setException(ae.getMessage());

            redirectAttributes.addFlashAttribute("command", trade);
            return "redirect:TradeException";
            //return new ModelAndView("TradeException", "command", trade);
        }
    }

    @RequestMapping(value = "/Trade", method = RequestMethod.POST)
    public ModelAndView processFinish(@ModelAttribute("trade") Trade trade, BindingResult result, RedirectAttributes redirectAttributes, ModelAndView modelAndView) {
        LOG.info("Entering function.");

        LOG.info("################### Sleep for 5 seconds...");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOG.error("Thread Interrupted.", e);
        }

        Customer customer = null;

        try {
            customer = customerService.getCustomer(user.getUsername());
            if (tradeService.tradeIsBuy(trade)) {
                tradeService.buyStock(customer, trade.getSymbol(), trade.getShares(), trade.getPrice());
            } else {
                tradeService.sellStock(customer, trade.getSymbol(), trade.getShares(), trade.getPrice());
            }

            redirectAttributes.addFlashAttribute("trade", trade);

            modelAndView.setViewName("redirect:/TradeAcknowledgeIntermediate.go");
            //modelAndView.addObject("trade", trade);

            return modelAndView;
        } catch (AppException ae) {
            LOG.error("Exception with data transaction: " + ae.getMessage());

            trade.setException(ae.getMessage());

            redirectAttributes.addFlashAttribute("command", trade);
            modelAndView.setViewName("redirect:TradeException");

            return modelAndView;
        }
    }

    /**
     * It cannot redirect to a page under WEB-INF directory. It has to go through an intermediate stage.
     * @param model
     * @return 
     */
    @RequestMapping(value = "/TradeAcknowledgeIntermediate")
    public String intermediate(@ModelAttribute("trade") Trade trade, Model model) {
        LOG.info("Entering function.");

        model.addAttribute("trade", trade);

        return "TradeAcknowledge";
    }

    @RequestMapping(value = "/TradeAcknowledgeIntermediate2")
    public ModelAndView intermediate2(@ModelAttribute("trade") Trade trade, ModelAndView modelAndView) {
        LOG.info("Entering function.");

        modelAndView.setViewName("TradeAcknowledge");
        modelAndView.addObject("trade", trade);

        return modelAndView;
    }

    @RequestMapping(value = "/Cancel", method = RequestMethod.GET)
    public String processCancel() {
        LOG.info("Entering function.");

        return "redirect:/Portfolio.go";
    }

}
