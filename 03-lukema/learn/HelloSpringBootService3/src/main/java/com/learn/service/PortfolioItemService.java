package com.learn.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.learn.entity.PortfolioItem;
import com.learn.exception.AppException;


@Service
public interface PortfolioItemService {

    public List<PortfolioItem> getActivePortfolioItems()
        throws AppException;

    public List<PortfolioItem> getPortfolioItemsByCity(String city)
        throws AppException;

    public List<PortfolioItem> getPortfolioItemsByZip(String zip)
        throws AppException;

    public List<PortfolioItem> getPortfolioItemsByState(String state)
        throws AppException;
}
