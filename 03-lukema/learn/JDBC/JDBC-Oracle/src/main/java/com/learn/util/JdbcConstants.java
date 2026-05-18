package com.learn.util;


public class JdbcConstants {

    // public static final String USER = "luke";

    // public static final String PASSWD = "luke";

    public static final String USER = "MA000020";

    public static final String PASSWD = "kesD:1kesD:1";

    public static final String OracleDriver = "oracle.jdbc.OracleDriver";

    public static final String URL_XE = "jdbc:oracle:thin:@localhost:1521:XE";

    public static final String URL_CIS = "jdbc:oracle:thin:@ecorrcip.cwqcqn8qo2yo.us-east-1.rds.amazonaws.com:1521/ECORRCIP";

    // public static final String URL_USPS = "jdbc:oracle:thin:@eagnmnmed5a3:1521/deems.usps.gov";
    // public static final String URL_USPS = "jdbc:oracle:thin:@eagnmnmed110.usps.gov:1521/dnav.usps.gov";
    // public static final String URL_USPS = "jdbc:oracle:thin:@eagnmnmed5a3:1521/deems";
    public static final String URL_USPS = "jdbc:oracle:thin:@(Description=(Failover=ON)(Address_List=(Load_Balance=ON)(address=(protocol=tcp)(host=eagnmnmed10e)(port=1521))(address=(protocol=tcp)(host=eagnmnmed10f)(port=1521)))(Connect_data=(service_name=tefms.usps.gov)(SERVER=DEDICATED)))";

    public static final String URL_EcorTST = "jdbc:oracle:thin:@(Description=(Failover=ON)(Address_List=(Load_Balance=ON)(address=(protocol=tcp)(host=db11v-1.test.econ.census.gov)(port=1630))(address=(protocol=tcp)(host=db11v-2.test.econ.census.gov)(port=1630)))(Connect_data=(service_name=EcorTst)(SERVER=DEDICATED)))";
    
    public static final String URL_EcorTST_2 = "jdbc:oracle:thin:@db11v-1.test.econ.census.gov:1630/ecortst";

    public static final String DB_HOST = "eagnmnmed5a3";

    public static final String DB_PORT = "1521";

    public static final String DB_SERVICE = "deems.usps.gov";

    public static final String url_usps_deems = "jdbc:oracle:thin:@(description=(address=(host=" + DB_HOST + ")(protocol=tcp)(port=" + DB_PORT
            + "))(connect_data=(service_name=" + DB_SERVICE + ")))";

    public static final String URL = URL_EcorTST_2;

    public static final String SQL_SELECT_SYSDATE = "select sysdate from dual";
    
    public static final String SQL_SELECT_SYSDATE_PARM = "select sysdate from dual where 100 != :num and sysdate != :dat";

    public static final String SQL_PROC_EMP = "{ call emp_package.select_emp(?, ?) }";

}
