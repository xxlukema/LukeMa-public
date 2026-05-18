package com.broadsoft.cpbx.e911.servlet;

import javax.servlet.annotation.WebServlet;

import org.atmosphere.cpr.AtmosphereServlet;

import com.broadsoft.cpbx.e911.provider.GlobalEventBusFactory;

/**
 * Servlet 3.0 that will scan for the {@link CometStatusHandler} and load it
 * as the framework.
 * @author chris
 *
 */
@WebServlet(asyncSupported=true, loadOnStartup = 1, urlPatterns="/async/*")
public class CometServlet extends AtmosphereServlet {

	private static final long serialVersionUID = -1598681205378912277L;
	
	private static final GlobalEventBusFactory eventBusFactory = new GlobalEventBusFactory();
	
	public CometServlet() {
		framework.addAtmosphereHandler("/async/status", new CometStatusHandler(eventBusFactory.provide()));
		System.out.println("SCANNING FOR APPLICATIONS");
		System.out.println("Servlet path is " + framework.getHandlersPath());
	}
}
