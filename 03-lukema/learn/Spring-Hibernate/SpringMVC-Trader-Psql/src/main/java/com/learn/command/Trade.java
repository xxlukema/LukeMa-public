package com.learn.command;


import java.io.Serializable;


public class Trade
    implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final boolean BUY = true;

    public static final boolean SELL = false;

    private boolean buySell;

    private String symbol;

    private int shares;

    private float price;

    private String exception;

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public boolean isBuySell() {
        return buySell;
    }

    public void setBuySell(boolean buySell) {
        this.buySell = buySell;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int quantity) {
        this.shares = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
