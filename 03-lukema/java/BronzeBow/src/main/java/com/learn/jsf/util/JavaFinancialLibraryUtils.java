package com.learn.jsf.util;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.neurotech.quotes.Quote;
import net.neurotech.quotes.QuoteException;
import net.neurotech.quotes.QuoteFactory;


public class JavaFinancialLibraryUtils {
    private static final Logger LOG = LogManager.getLogger();

    private static final long ThreadTimeOutMiliseconds = 400;

    private static final long KeepAliveTime = 500;

    public static final ThreadPoolExecutor QuoteThreadPoolExecutor = new ThreadPoolExecutor(6, 6,
            KeepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(6),
            new RejectedExecutionHandler() {
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    LOG.error("Rejected task for quote: " + ((QuoteRunnable) r).getSymbol());
                }
            });

    public static Quote getQuoteBlock(String symbol) {
        try {
            QuoteFactory quoteFactory = new QuoteFactory();
            Quote quote = quoteFactory.getQuote(symbol);

            return quote;
        }
        catch (QuoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static Quote getQuoteWait(String symbol) {
        QuoteRunnable quoteRunnable = new QuoteRunnable();
        quoteRunnable.setSymbol(symbol);

        long startMilisec = System.currentTimeMillis();

        QuoteThreadPoolExecutor.execute(quoteRunnable);

        try {
            QuoteThreadPoolExecutor.awaitTermination(ThreadTimeOutMiliseconds, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e) {
        }

        float time = JsfUtils.timeInSecondsFromStart(startMilisec);
        LOG.debug("Time (seconds) to get Quote: " + time);

        return quoteRunnable.getQuote();
    }

    public static boolean symbolIsValid(String symbol) {
        if (symbol == null) {
            return false;
        }

        symbol = symbol.trim();

        if (symbol.equals("")) {
            return false;
        }

        try {
            getQuoteBlock(symbol);
            return true;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    protected void finalize() {
        QuoteThreadPoolExecutor.shutdown();
        LOG.debug("QuoteThreadPoolExecutor was shutdown.");
    }
}
