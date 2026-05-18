package com.learn.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.bean.BeanBase;
import com.learn.dao.CommonDAO;
import com.learn.service.CommonService;


@Service("commonService")
public class CommonServiceImpl
    implements CommonService {

    @Autowired
    @Qualifier("commonDAO")
    private CommonDAO commonDAO;

    public <T extends BeanBase> List<T> list(Class<T> clazz) {
        return commonDAO.list(clazz);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public <T extends BeanBase> T saveOrUpdate(T bean) {
        return commonDAO.saveOrUpdate(bean);
    }

}
