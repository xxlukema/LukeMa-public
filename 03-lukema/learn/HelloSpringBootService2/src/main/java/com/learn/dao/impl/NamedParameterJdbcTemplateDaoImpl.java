package com.learn.dao.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.learn.dao.NamedParameterJdbcTemplateDao;
import com.learn.dao.rowmapper.SysdateRow;
import com.learn.dao.rowmapper.SysdateRowMapper;
import com.learn.pojo.CurrentDatePojo;
import com.learn.util.EjbConstants;


@Repository("namedParameterJdbcTemplateDao")
public class NamedParameterJdbcTemplateDaoImpl
    implements NamedParameterJdbcTemplateDao {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CurrentDatePojo selectCurrentDateEntityManager() {
        LOG.info("Entering function.");

        TypedQuery<CurrentDatePojo> query = entityManager.createNamedQuery(EjbConstants.Select_Current_Date, CurrentDatePojo.class);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date before = calendar.getTime();

        query.setParameter("dat", before).setParameter("num", 1);

        return query.getSingleResult();
    }

    @Override
    public CurrentDatePojo selectCurrentDateJdbcTemplate() {

        LOG.info("Entering function.");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date before = calendar.getTime();

        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("num", 1);
        namedParameters.put("dat", before);

        List<SysdateRow> list = namedParameterJdbcTemplate.query(SysdateRowMapper.SQL_SELECT_SYSDATE_PARM, namedParameters, new SysdateRowMapper());

        LOG.debug("list.size() = " + list.size());

        for (SysdateRow row : list) {
            LOG.debug(row.getDate());
        }

        CurrentDatePojo currentDatePojo = new CurrentDatePojo();
        currentDatePojo.setDate(list.get(0).getDate());
        currentDatePojo.setNote("desc");

        return currentDatePojo;
    }
}
