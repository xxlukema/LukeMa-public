package com.learn.dao.impl;


import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.learn.dao.CommonDAO;
import com.learn.pojo.CurrentDatePojo;
import com.learn.util.EjbConstants;


@Repository("commonDAO")
public class CommonDAOImpl
    implements CommonDAO {

    private static final Logger LOG = LogManager.getLogger();

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CurrentDatePojo selectCurrentDate() {
        LOG.info("Entering function.");

        TypedQuery<CurrentDatePojo> query = entityManager.createNamedQuery(EjbConstants.Select_Current_Date, CurrentDatePojo.class);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date before = calendar.getTime();

        query.setParameter("dat", before);
        query.setParameter("num", 1);

        return query.getSingleResult();
    }
}
