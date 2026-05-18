package com.learn.service.impl;


import java.util.List;

import com.learn.bean.BeanBase;
import com.learn.dao.CommonDAO;
import com.learn.service.CommonService;


public class CommonServiceImpl
   implements CommonService
{
   private CommonDAO commonDAO;

   public <T extends BeanBase> List<T> list(Class<T> clazz)
   {
      return getCommonDAO().list(clazz);
   }

   public <T extends BeanBase> T saveOrUpdate(T bean)
   {
      return getCommonDAO().saveOrUpdate(bean);
   }

   public void setCommonDAO(CommonDAO commonDAO)
   {
      this.commonDAO = commonDAO;
   }

   public CommonDAO getCommonDAO()
   {
      return commonDAO;
   }

}
