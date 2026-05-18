package com.learn.test;


import java.util.Date;

import javax.annotation.Resource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-beans.xml" })
public class MyControllerTest {

    private static final Logger LOG = LogManager.getLogger();

    @Resource
    private Date date;

    @Autowired
    private MyController myController;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        request.setMethod("POST");
    }

    @Test
    public void doGetIndexPage()
        throws Exception {
        
        LOG.debug("Start test.");
        
        ModelAndView modelAndView = myController.handleRequest(request, response);

        Assert.assertEquals(modelAndView.getViewName(), "index.jsp");
        
        LOG.debug("End test.");
    }

    @Test
    public void doSearch()
        throws Exception {
        
        LOG.debug("Start test.");
        
        request.addParameter("query", "testing");
        ModelAndView modelAndView = myController.handleRequest(request, response);

        Assert.assertEquals(modelAndView.getViewName(), "results.jsp");
        
        LOG.debug("End test.");
    }

    @Test
    public void testDate() {
        
        LOG.debug("Start test.");
        
        LOG.info("Date = " + date);

        Assert.assertNotNull(date);
        
        LOG.debug("End test.");
    }

}
