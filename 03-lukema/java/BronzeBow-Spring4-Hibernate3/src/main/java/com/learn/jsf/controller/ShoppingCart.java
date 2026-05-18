package com.learn.jsf.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class ShoppingCart implements Serializable {
	private static final long serialVersionUID = 1L;

	protected static final Logger LOG = Logger.getLogger(ShoppingCart.class);

	private int numberOfItems;

	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}

}
