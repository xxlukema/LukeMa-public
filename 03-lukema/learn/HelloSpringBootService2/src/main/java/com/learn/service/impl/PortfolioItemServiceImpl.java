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

    // private static final Logger LOG = LogManager.getLogger();

    //@Autowired
    //@Qualifier("portfolioItemDAO")
    //private PortfolioItemDAO portfolioItemDAO;

    @Override
    public List<PortfolioItem> getActivePortfolioItems()
        throws AppException {

        //return portfolioItemDAO.getActivePortfolioItems();
        return null;
    }

    @Override
    public List<PortfolioItem> getPortfolioItemsByCity(String city)
        throws AppException {

        //return portfolioItemDAO.getPortfolioItemsByCity(city);
        return null;
    }

    @Override
    public List<PortfolioItem> getPortfolioItemsByZip(String zip)
        throws AppException {

        // return portfolioItemDAO.getPortfolioItemsByZip(zip);
        return null;
    }

    @Override
    public List<PortfolioItem> getPortfolioItemsByState(String state)
        throws AppException {

        return null;
        //return portfolioItemDAO.getPortfolioItemsByState(state);
    }

}
