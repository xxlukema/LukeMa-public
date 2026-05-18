package com.learn.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.learn.util.EJBLocator;

@ManagedBean
@SessionScoped
public class BookController {
	private static final Logger LOG = Logger.getLogger(BookController.class);

	// @EJB
	// protected BookSessionBeanLocal bookSessionBeanLocal;

	public String clickedAction() {
		LOG.debug("action: You clicked me.");

		System.out.println("Testing EJBLocator.testBean()...");

		EJBLocator.testBean();

		System.out.println("Tested EJBLocator.testBean().");

		EJBLocator.testMDB();

		// bookSessionBeanLocal.test();

		return "BookResult";
	}

	public void itemSelected(ActionEvent actionEvent) {
		LOG.debug("Inside departure actionListener of Controller.");
	}

}
