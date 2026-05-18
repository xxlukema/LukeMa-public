package com.broadsoft.cpbx.e911.domain;

import com.broadsoft.cpbx.e911.api.ISession;

public class WebSession implements ISession {

	private static WebSession instance = null;

	private String tn;
	
	private WebSession() {

	}

	public static final WebSession getInstance() {
		if (instance == null) {
			instance = new WebSession();
		}
		return instance;
	}

	@Override
	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
	    this.tn = tn;
	}
}
