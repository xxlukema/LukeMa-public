package com.learn.jsf.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.neurotech.quotes.Quote;
import net.neurotech.quotes.QuoteFactory;


public class QuoteRunnable
    implements Runnable {
    private static final Logger LOG = LogManager.getLogger();

    private Quote quote;
    private String symbol;

    public void run() {
        try {
            QuoteFactory quoteFactory = new QuoteFactory();
            quote = quoteFactory.getQuote(symbol);
        }
        catch (Throwable e) {
            LOG.error("Exception with quoteFactory.getQuote: " + symbol + " " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}
