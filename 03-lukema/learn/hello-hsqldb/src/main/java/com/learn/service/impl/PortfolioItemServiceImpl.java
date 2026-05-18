package com.learn.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.entity.PortfolioItem;
import com.learn.exception.AppException;
import com.learn.service.PortfolioItemService;


@Service("portfolioItemService")
@Transactional
public class PortfolioItemServiceImpl
    implements PortfolioItemService {

    // private static final Logger log = LogManager.getLogger();

    //@Autowired
    //@Qualifier("portfolioItemDAO")
    //private PortfolioItemDAO portfolioItemDAO;

    @Override
    public List<PortfolioItem> getActivePortfolioItems()
        throws AppException {
        try {
            //return portfolioItemDAO.getActivePortfolioItems();
            return null;
        } catch (Exception e) {
            throw new AppException("getActivePortfolioItems() Exception", e);
        }
    }

    @Override
    public List<PortfolioItem> getPortfolioItemsByCity(String city)
        throws AppException {
        try {
            //return portfolioItemDAO.getPortfolioItemsByCity(city);
            return null;
        } catch (Exception e) {
            throw new AppException("getPortfolioItemsByCity() Exception", e);
        }
    }

    @Override
    public List<PortfolioItem> getPortfolioItemsByZip(String zip)
        throws AppException {
        try {
            // return portfolioItemDAO.getPortfolioItemsByZip(zip);
            return null;
        } catch (Exception e) {
            throw new AppException("getPortfolioItemsByZip() Exception", e);
        }
    }

    @Override
    public List<PortfolioItem> getPortfolioItemsByState(String state)
        throws AppException {
        try {
            return null;
            //return portfolioItemDAO.getPortfolioItemsByState(state);
        } catch (Exception e) {
            throw new AppException("getPortfolioItemsByState() Exception", e);
        }
    }

}
