package com.learn.jsf.controller;


import java.io.Serializable;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;


@Scope("session")
@Named
public class ShoppingCart
    implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numberOfItems;

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

}
