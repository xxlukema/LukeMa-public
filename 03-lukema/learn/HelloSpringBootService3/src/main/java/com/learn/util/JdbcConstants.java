package com.learn.util;


public class JdbcConstants {

    public static final String SQL_SELECT_SYSDATE = "SELECT current_date as date, 'desc' as note";

    public static final String SQL_SELECT_SYSDATE_PARM = "SELECT current_date as date WHERE 100 != :num and current_date != :dat";
    
    public static final String SQL_SELECT_SYSDATE_NAMED_QUERY = "SELECT current_date as date, 'desc' as note WHERE 100 != :num and current_date != :dat";

}
