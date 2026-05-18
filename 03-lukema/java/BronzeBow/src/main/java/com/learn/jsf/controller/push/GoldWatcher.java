package com.learn.jsf.controller.push;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class GoldWatcher extends BaseQuoteWatcher {
	private static final long serialVersionUID = 1L;

	public String getSymbol() {
		// gcz09.cmx GCF10.CMX gcz09.cmx
		return "gcz10.cmx";
	}
}
