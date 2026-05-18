package com.learn.test;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
@ContextConfiguration(locations = { "/test-beans.xml" })
public class MyControllerTest {

	protected Logger LOG = Logger.getLogger(MyControllerTest.class);

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
	public void doGetIndexPage() throws Exception {
		ModelAndView modelAndView = myController.handleRequest(request,
				response);

		Assert.assertEquals(modelAndView.getViewName(), "index.jsp");
	}

	@Test
	public void doSearch() throws Exception {
		request.addParameter("query", "testing");
		ModelAndView modelAndView = myController.handleRequest(request,
				response);

		Assert.assertEquals(modelAndView.getViewName(), "results.jsp");
	}

	@Test
	public void testDate() {
		LOG.info("Date = " + date);

		Assert.assertNotNull(date);
	}

}
