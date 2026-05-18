package com.learn.persistence.dao.impl;


import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.learn.persistence.bean.AccessBlocked;
import com.learn.persistence.bean.AccessHotList;
import com.learn.persistence.bean.AccessRecord;
import com.learn.persistence.dao.AccessDAO;


@Repository("accessDAO")
public class AccessDAOImpl
    extends CommonDAOImpl
    implements AccessDAO {

    private static final long serialVersionUID = 1L;

    @Autowired
    private AccessRecordsCleanupStoredProcedure accessRecordsCleanupStoredProcedure;

    @Autowired
    private AccessAlarmsCleanupStoredProcedure accessAlarmsCleanupStoredProcedure;

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public int getRecentAccessCount(String remoteAddress, Date from) {

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) sessionFactory.getCurrentSession().getNamedQuery(AccessRecord.GetRecentAccessCount).setString("remoteAddress", remoteAddress)
                .setDate("date", from).list();

        return list.get(0).intValue();
    }

    public boolean isAccessBlocked(String remoteAddress) {

        @SuppressWarnings("unchecked")
        List<AccessBlocked> list = (List<AccessBlocked>) sessionFactory.getCurrentSession().createQuery("from AccessBlocked where remoteAddress = :remoteAddress")
                .setString("remoteAddress", remoteAddress).list();

        return list.size() > 0;
    }

    public List<AccessHotList> retrieveAccessHotList(int hotListSize) {

        @SuppressWarnings("unchecked")
        List<AccessHotList> list = (List<AccessHotList>) sessionFactory.getCurrentSession().getNamedQuery(AccessHotList.RetrieveAccessHotList).setMaxResults(hotListSize)
                .list();

        return list;
    }

    public AccessHotList findByExample(AccessHotList exampleEntity) {
        @SuppressWarnings("unchecked")
        List<AccessHotList> list = (List<AccessHotList>) sessionFactory.getCurrentSession().createQuery("from AccessHotList where symbol = :symbol")
                .setString("symbol", exampleEntity.getSymbol()).list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public void shrinkAccessRecords() {
        accessRecordsCleanupStoredProcedure.execute();
    }

    public void shrinkAccessAlarms() {
        accessAlarmsCleanupStoredProcedure.execute();
    }

    public AccessHotList findAccessHotListBySymbol(String symbol)
        throws Exception {
        @SuppressWarnings("unchecked")
        List<AccessHotList> list = (List<AccessHotList>) sessionFactory.getCurrentSession().getNamedQuery(AccessHotList.FindAccessHotListBySymbol)
                .setString("symbol", symbol).list();

        if (list.size() == 1) {
            return (AccessHotList) list.get(0);
        } else if (list.size() > 1) {
            throw new Exception("AccessHotList cannot have duplicated symbol.");
        }

        return null;
    }

    public void updateAccessHotList(String symbol) {
        AccessHotList accessHotList = null;
        try {
            accessHotList = findAccessHotListBySymbol(symbol);
        } catch (Exception e) {
        }

        if (accessHotList == null) {
            accessHotList = new AccessHotList();
            accessHotList.setSymbol(symbol);
            accessHotList.setUpdateDate(new Date());
            accessHotList.setAccessCounter(1);
            save(accessHotList);
        } else {
            accessHotList.setUpdateDate(new Date());
            accessHotList.setAccessCounter(accessHotList.getAccessCounter() + 1);
            update(accessHotList);
        }
    }
}
