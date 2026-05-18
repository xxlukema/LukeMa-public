package com.learn.jsf.controller.push;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

@ManagedBean
@ApplicationScoped
public class DowWatcher extends BaseQuoteWatcher {
	private static final long serialVersionUID = 1L;

	protected static final Logger LOG = Logger.getLogger(DowWatcher.class);

	public String getSymbol() {
		return "^dji";
	}

}
