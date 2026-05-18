package com.learn.jsf.controller.push;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class QqqWatcher extends BaseQuoteWatcher {
	private static final long serialVersionUID = 1L;

	public String getSymbol() {
		return "qqq";
	}
}
