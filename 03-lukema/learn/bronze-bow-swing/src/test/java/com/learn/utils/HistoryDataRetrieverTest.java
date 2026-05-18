package com.learn.utils;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.stock.StockQuote;


/**
 * https://financequotes-api.com/ 
 */
@Log4j2
public class HistoryDataRetrieverTest {

    @Test
    public void testGet()
        throws Exception {
        log.debug("Begin Test.");

        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.MONTH, -2); // from 2 month ago

        Stock stock = YahooFinance.get("UAL", from, to, Interval.DAILY);

        StockQuote stockQuote = stock.getQuote();

        BigDecimal price = stockQuote.getPrice();
        BigDecimal change = stockQuote.getChangeInPercent();
        BigDecimal open = stockQuote.getOpen();
        BigDecimal close = stockQuote.getPreviousClose();
        BigDecimal high = stockQuote.getDayHigh();
        BigDecimal low = stockQuote.getDayLow();

        stock.print();

        List<HistoricalQuote> list = stock.getHistory();
        
        list.forEach(item -> {
            log.debug(item.toString());
        });

        // log.debug("History data: {}", list);

        log.debug("price: {}, change: {}, open: {}, close: {}, high: {}, low: {}", price, change, open, close, high, low);

        log.debug("End Test.");
    }

}
