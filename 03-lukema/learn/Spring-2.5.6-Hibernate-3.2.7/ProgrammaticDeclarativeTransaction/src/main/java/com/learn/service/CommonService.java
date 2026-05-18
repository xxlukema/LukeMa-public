package com.learn.service;


import java.util.List;

import com.learn.bean.BeanBase;


public interface CommonService
{
   public <T extends BeanBase> List<T> list(Class<T> clazz);

   public <T extends BeanBase> T saveOrUpdate(T bean);

}
