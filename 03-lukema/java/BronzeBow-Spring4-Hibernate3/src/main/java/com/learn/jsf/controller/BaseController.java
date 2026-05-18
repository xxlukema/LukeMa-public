package com.learn.jsf.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import com.learn.persistence.bean.User;

public class BaseController implements Serializable {
	private static final long serialVersionUID = 1L;

	protected static final Logger LOG = Logger.getLogger(BaseController.class);

	@ManagedProperty(value = "#{userLoginController}")
	private UserLoginController userLoginController;

	public User getUser() {
		if (userLoginController == null) {
			LOG.error("############# getUser() userLoginController is null.");
		}
		return userLoginController.getUser();
	}

	public UserLoginController getUserLoginController() {
		if (userLoginController == null) {
			LOG.error("############# getUserLoginController() userLoginController is null.");
		}
		return userLoginController;
	}

	public void setUserLoginController(UserLoginController userLoginController) {
		this.userLoginController = userLoginController;
	}

}
