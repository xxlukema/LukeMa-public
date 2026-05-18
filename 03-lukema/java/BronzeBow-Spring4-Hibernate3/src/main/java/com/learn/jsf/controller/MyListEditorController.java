package com.learn.jsf.controller;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

import com.learn.jsf.util.PageUrls;
import com.learn.jsf.validator.StockSymbolValidator;
import com.learn.persistence.service.AppException;
import com.learn.persistence.util.SpringServiceFacade;


@ManagedBean
public class MyListEditorController
    extends BaseController {
    private static final long serialVersionUID = 1L;

    protected static final Logger LOG = Logger.getLogger(MyListEditorController.class);

    private String errorMessage;
    private boolean myListValid = false;

    public String save() {
        LOG.debug("Do save user stock list.");

        errorMessage = null;

        String myList = getUser().getStockList();
        if (myList != null) {
            List<String> list = new LinkedList<String>();
            String[] symbols = getUser().getStockList().split("[ \n\r\t,;|]");
            for (String symbol : symbols) {
                LOG.debug("Validating stock symbol: ###" + symbol + "###");
                
                Matcher matcher = StockSymbolValidator.REGEXP_SYMBOL.matcher(symbol);

                if (!matcher.matches()) {
                    LOG.debug("Invalid stock symbol(s): " + symbol);

                    if (errorMessage == null) {
                        errorMessage = "Invalid stock symbol(s): " + symbol;
                    }

                    errorMessage += symbol + " ";
                } else {
                    list.add(symbol);
                }
            }

            if (list.size() > 30) {
                errorMessage = "The maximum number of stocks in user watch list is 30.";
            }

            Collections.sort(list);

            if (errorMessage == null) {
                StringBuffer sb = new StringBuffer();
                for (String symbol : list) {
                    sb.append(symbol).append(' ');
                }

                myList = sb.toString().toLowerCase().trim();

                if (myList.length() == 0) {
                    myList = null;
                } else {
                    getUser().setStockList(myList);
                    try {
                        SpringServiceFacade.saveOrUpdate(getUser());
                    } catch (AppException e) {
                        errorMessage = e.getMessage();
                        LOG.error("Unabble to save user to database.", e);
                    }
                }
            }
        }

        if (errorMessage == null) {
            try {
                getUser().setStockList(myList);
                SpringServiceFacade.saveOrUpdate(getUser());
            } catch (AppException e) {
                errorMessage = e.getMessage();
                LOG.error("Unabble to save user to database.", e);
            }

            return PageUrls.Ta;
        } else {
            return PageUrls.MyListEditor;
        }
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setMyListValid(boolean myListValid) {
        this.myListValid = myListValid;
    }

    public boolean isMyListValid() {
        return myListValid;
    }

}
