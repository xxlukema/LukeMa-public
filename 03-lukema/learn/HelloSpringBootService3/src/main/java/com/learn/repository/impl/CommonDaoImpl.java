package com.learn.repository.impl;


import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.learn.pojo.CurrentDatePojo;
import com.learn.repository.CommonRepository;
import com.learn.util.EjbConstants;


@Repository("commonDao")
public class CommonDaoImpl
    implements CommonRepository {

    private static final Logger log = LogManager.getLogger();

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CurrentDatePojo selectCurrentDate() {
        log.info(() -> "Entering function.");

        TypedQuery<CurrentDatePojo> query = entityManager.createNamedQuery(EjbConstants.Select_Current_Date, CurrentDatePojo.class);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date before = calendar.getTime();

        query.setParameter("dat", before);
        query.setParameter("num", 1);

        return query.getSingleResult();
    }
}
