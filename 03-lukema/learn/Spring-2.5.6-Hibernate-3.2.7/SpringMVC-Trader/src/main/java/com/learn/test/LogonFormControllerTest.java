package com.learn.test;

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
import org.springframework.web.servlet.view.RedirectView;

import com.learn.command.Credentials;
import com.learn.controller.LogonFormController;
import com.learn.util.StringConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/SpringMVC-Trader-servlet.xml"})
public class LogonFormControllerTest {

	protected Logger LOG = Logger.getLogger(LogonFormControllerTest.class);

	@Autowired
	private LogonFormController logonFormController;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest("POST", "/logon.go");
		response = new MockHttpServletResponse();

		// request.setMethod("POST");

		logonFormController.setCommandClass(Credentials.class);
	}

	@Test
	public void onSubmit() throws Exception {
		Credentials credentials = new Credentials();
		credentials.setUsername(StringConstants.GUEST_USERNAME);
		credentials.setPassword(StringConstants.GUEST_PASSWORD);

		// request.addParameter("username", "NewUserName");

		ModelAndView modelAndView = logonFormController.handleRequest(request,
				response);

		String url = ((RedirectView) modelAndView.getView()).getUrl();
		LOG.info("url = " + url);

		LOG.info("modelAndView.getViewName() = " + modelAndView.getViewName());

		Assert.assertEquals(logonFormController.getSuccessView(), url);
	}

	@Test
	public void onUnauthorizedUser() throws Exception {
		Credentials credentials = new Credentials();
		credentials.setUsername(StringConstants.GUEST_USERNAME);
		credentials.setPassword(StringConstants.GUEST_PASSWORD);

		request.addParameter("password", "NewPassword");

		ModelAndView modelAndView = logonFormController.handleRequest(request,
				response);

		Assert.assertEquals(logonFormController.getFormView(),
				modelAndView.getViewName());
	}
}
