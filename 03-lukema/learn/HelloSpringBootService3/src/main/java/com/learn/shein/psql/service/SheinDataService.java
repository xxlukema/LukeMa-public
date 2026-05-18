package com.learn.shein.psql.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learn.exception.AppException;
import com.learn.shein.psql.entity.Country;
import com.learn.shein.psql.entity.SheinProduct;
import com.learn.shein.psql.entity.SheinProductImage;
import com.learn.shein.psql.repository.CountryRepository;
import com.learn.shein.psql.repository.SheinProductImageRepository;
import com.learn.shein.psql.repository.SheinProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class SheinDataService {

    /**
     * 1. This is a good candidate for "constructor" injection.
     * 2. The member must have `final` modifier.
     * 3. Property injection is used for learning purpose only.
     */

    /**
     * Implicit constructor injection
     */
    // Auto generated constructor by lombok

    // @Autowired
    private final CountryRepository countryRepository;

    // @Autowired
    private final SheinProductRepository sheinProductRepository;

    // @Autowired
    private final SheinProductImageRepository sheinProductImageRepository;

    @Transactional
    public Iterable<Country> findAllCountries()
        throws AppException {
        try {
            Iterable<Country> all = countryRepository.findAll();
            return all;
        } catch (Exception e) {
            throw new AppException("countryDao.findAll() Exception", e);
        }
    }

    @Transactional
    public Iterable<SheinProduct> findAllSheinProducts()
        throws AppException {
        try {
            Iterable<SheinProduct> all = sheinProductRepository.findAll();
            return all;
        } catch (Exception e) {
            throw new AppException("sheinProductDao.findAll() Exception", e);
        }
    }

    @Transactional
    public Optional<SheinProduct> findSheinProductById(Long productId)
        throws AppException {
        try {
            Optional<SheinProduct> prod = sheinProductRepository.findById(productId);
            return prod;
        } catch (Exception e) {
            throw new AppException("sheinProductDao.findById() Exception", e);
        }
    }

    @Transactional
    public SheinProduct save(SheinProduct sheinProduct)
        throws AppException {
        try {
            return sheinProductRepository.save(sheinProduct);
        } catch (Exception e) {
            throw new AppException("sheinProductDao.save(sheinProduct) Exception", e);
        }
    }

    @Transactional
    public SheinProductImage save(SheinProductImage sheinProductImage)
        throws AppException {
        try {
            return sheinProductImageRepository.save(sheinProductImage);
        } catch (Exception e) {
            throw new AppException("sheinProductImageDao.save(sheinProductImage) Exception", e);
        }
    }

}
