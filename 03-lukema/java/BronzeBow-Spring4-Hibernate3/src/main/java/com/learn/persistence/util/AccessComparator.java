package com.learn.persistence.util;


import java.util.Comparator;

import com.learn.persistence.bean.AccessHotList;


public class AccessComparator
   implements Comparator<AccessHotList>
{

   public int compare(AccessHotList o1, AccessHotList o2)
   {
      return o1.getSymbol().compareTo(o2.getSymbol());
   }

}
