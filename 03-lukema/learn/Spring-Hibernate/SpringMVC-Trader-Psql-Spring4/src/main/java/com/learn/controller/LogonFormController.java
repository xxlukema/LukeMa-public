package com.learn.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.learn.bean.Customer;
import com.learn.command.Credentials;
import com.learn.service.AppException;
import com.learn.service.CustomerService;
import com.learn.session.User;
import com.learn.util.StringConstants;


@Transactional
@Controller
public class LogonFormController {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private CustomerService customerService;

    @Autowired
    private User user;

    @RequestMapping(value = "/Logon", method = RequestMethod.GET)
    public ModelAndView init(@ModelAttribute("credentials") Credentials credentials)
        throws AppException {

        LOG.debug("Inside init()...");

        credentials.setUsername(StringConstants.GUEST_USERNAME);
        credentials.setPassword(StringConstants.GUEST_PASSWORD);

        ModelAndView modelAndView = new ModelAndView("Logon");

        /** JNDI Test
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/luke");
            conn = ds.getConnection();
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("select sysdate from dual");
        
            while (resultSet.next()) {
                Date date = resultSet.getDate("sysdate");
        
                LOG.info("Date: " + date);
            }
        
        } catch (Exception e) {
            LOG.error("JNDI Exception", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOG.error("resultSet Exception", e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    LOG.error("stmt Exception", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error("conn Exception", e);
                }
            }
        }
        */

        return modelAndView;
    }

    @RequestMapping(value = "/Logon", method = RequestMethod.POST)
    public String doLogon(@ModelAttribute("credentials") Credentials credentials)
        throws AppException {

        LOG.debug("Inside doLogon()...");

        String username = credentials.getUsername();
        String password = credentials.getPassword();

        LOG.debug("username: " + username);
        LOG.debug("password: " + password);

        if (username.equals(StringConstants.GUEST_USERNAME)) {

            Customer customer = null;

            try {
                customer = customerService.getCustomer(username);
            } catch (AppException ae) {
                throw new AppException("Exception getting Customer.", ae);
            }

            if (customer.getPassword().equals(password)) {
                user.setUsername(username);

                LOG.debug("User logged on.");

                return "redirect:Portfolio.go";
            }
        }

        LOG.debug("User NOT logged on.");

        return null;
    }

    @RequestMapping(value = "/Logout")
    public void doLogout()
        throws AppException {
        user.setUsername(null);

        LOG.debug("User logout completed.");
    }

}
