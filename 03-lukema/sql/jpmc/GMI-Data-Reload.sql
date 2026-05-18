In QA
_____
      
drop table MO_GMI_STG_ACCOUNTB;
drop table MO_GMI_STG_ACCOUNT_TYPEB;
drop table MO_GMI_STG_ACCT_CLASSB;
drop table MO_GMI_STG_CASH_POSITIONB;
drop table MO_GMI_STG_COLLATERAL_DETAILB;
drop table MO_GMI_STG_CURRENCY_RATEB;
drop table MO_GMI_STG_EXCHANGE_CODEB;
drop table MO_GMI_STG_FIRMB;
drop table MO_GMI_STG_MARGIN_SUMMARYB;
drop table MO_GMI_STG_PRMS_IMB;
drop table MO_GMI_STG_PRODUCTB;
drop table MO_GMI_STG_SECURITY_POSITIONB;
drop table MO_GMI_STG_TRADEB;

create table MO_GMI_STG_ACCOUNTB as select * from MO_GMI_STG_ACCOUNT where cob_date = '09-June-2011';
create table MO_GMI_STG_ACCOUNT_TYPEB as select * from MO_GMI_STG_ACCOUNT_TYPE where cob_date = '09-June-2011';
create table MO_GMI_STG_ACCT_CLASSB as select * from MO_GMI_STG_ACCT_CLASS where cob_date = '09-June-2011';
create table MO_GMI_STG_CASH_POSITIONB as select * from MO_GMI_STG_CASH_POSITION where cob_date = '09-June-2011';
create table MO_GMI_STG_COLLATERAL_DETAILB as select * from MO_GMI_STG_COLLATERAL_DETAIL where cob_date = '09-June-2011';
create table MO_GMI_STG_CURRENCY_RATEB as select * from MO_GMI_STG_CURRENCY_RATE where cob_date = '09-June-2011';
create table MO_GMI_STG_EXCHANGE_CODEB as select * from MO_GMI_STG_EXCHANGE_CODE where cob_date = '09-June-2011';
create table MO_GMI_STG_FIRMB as select * from MO_GMI_STG_FIRM where cob_date = '09-June-2011';
create table MO_GMI_STG_MARGIN_SUMMARYB as select * from MO_GMI_STG_MARGIN_SUMMARY where cob_date = '09-June-2011';
create table MO_GMI_STG_PRMS_IMB as select * from MO_GMI_STG_PRMS_IM where cob_date = '09-June-2011';
create table MO_GMI_STG_PRODUCTB as select * from MO_GMI_STG_PRODUCT where cob_date = '09-June-2011';
create table MO_GMI_STG_SECURITY_POSITIONB as select * from MO_GMI_STG_SECURITY_POSITION where cob_date = '09-June-2011';
create table MO_GMI_STG_TRADEB as select * from MO_GMI_STG_TRADE where cob_date = '09-June-2011';
	  
UPDATE MO_GMI_STG_PRMS_IMB           set cob_date = '25-May-2011';    
UPDATE MO_GMI_STG_SECURITY_POSITIONB set cob_date = '25-May-2011';    
UPDATE MO_GMI_STG_PRODUCTB           set cob_date = '25-May-2011';    
UPDATE MO_GMI_STG_MARGIN_SUMMARYB    set cob_date = '25-May-2011';    
UPDATE MO_GMI_STG_FIRMB              set cob_date = '25-May-2011';    
UPDATE MO_GMI_STG_EXCHANGE_CODEB     set cob_date = '25-May-2011';    
UPDATE MO_GMI_STG_CURRENCY_RATEB     set cob_date = '25-May-2011';    
UPDATE MO_GMI_STG_CASH_POSITIONB     set cob_date = '25-May-2011';    
UPDATE MO_GMI_STG_ACCOUNT_TYPEB      set cob_date = '25-May-2011';    
UPDATE MO_GMI_STG_ACCOUNTB           set cob_date = '25-May-2011';    
UPDATE MO_GMI_STG_TRADEB             set cob_date = '25-May-2011';    
UPDATE MO_GMI_STG_ACCT_CLASSB        set cob_date = '25-May-2011';    
UPDATE MO_GMI_STG_COLLATERAL_DETAILB set cob_date = '25-May-2011';                                                                                                                
                                                                                                            
In UAT                                                                                                            

CREATE PUBLIC DATABASE LINK LINK_QA.WORLD
CONNECT TO OPS$COAST
Identified by c0ast_1337
Using '(DESCRIPTION=
    (FAILOVER=on)
    (LOAD_BALANCE=on)
    (ADDRESS_LIST=
      (ADDRESS=
        (PROTOCOL=TCP)
        (HOST=coastqadb1.us.jpmchase.net)
        (PORT=22430)
      )
      (ADDRESS=
        (PROTOCOL=TCP)
        (HOST=coastqadb2.us.jpmchase.net)
        (PORT=22430)
      )
    )
    (CONNECT_DATA=
      (SERVICE_NAME=COASTQ.WORLD)
    )
  )';
                                                                                                            
                                                                                                            
                                                                                                            
                                                                                                            
                                                                                                            
                                                                                                            
                                                                                                            
                                                                                                            
IN UAT
-----------                                                                                                            
INSERT INTO MO_GMI_STG_PRMS_IM              SELECT * FROM MO_GMI_STG_PRMS_IMB@link_qa.world             ;
INSERT INTO MO_GMI_STG_SECURITY_POSITION    SELECT * FROM MO_GMI_STG_SECURITY_POSITIONB@link_qa.world   ;
INSERT INTO MO_GMI_STG_PRODUCT              SELECT * FROM MO_GMI_STG_PRODUCTB@link_qa.world             ;
INSERT INTO MO_GMI_STG_MARGIN_SUMMARY       SELECT * FROM MO_GMI_STG_MARGIN_SUMMARYB@link_qa.world      ;
INSERT INTO MO_GMI_STG_FIRM                 SELECT * FROM MO_GMI_STG_FIRMB@link_qa.world                ;
INSERT INTO MO_GMI_STG_EXCHANGE_CODE        SELECT * FROM MO_GMI_STG_EXCHANGE_CODEB@link_qa.world       ;
INSERT INTO MO_GMI_STG_CURRENCY_RATE        SELECT * FROM MO_GMI_STG_CURRENCY_RATEB@link_qa.world       ;
INSERT INTO MO_GMI_STG_CASH_POSITION        SELECT * FROM MO_GMI_STG_CASH_POSITIONB@link_qa.world       ;
INSERT INTO MO_GMI_STG_ACCOUNT_TYPE         SELECT * FROM MO_GMI_STG_ACCOUNT_TYPEB@link_qa.world        ;
INSERT INTO MO_GMI_STG_ACCOUNT              SELECT * FROM MO_GMI_STG_ACCOUNTB@link_qa.world             ;
INSERT INTO MO_GMI_STG_TRADE                SELECT * FROM MO_GMI_STG_TRADEB@link_qa.world               ;
INSERT INTO MO_GMI_STG_ACCT_CLASS           SELECT * FROM MO_GMI_STG_ACCT_CLASSB@link_qa.world          ;
INSERT INTO MO_GMI_STG_COLLATERAL_DETAIL    SELECT * FROM MO_GMI_STG_COLLATERAL_DETAILB@link_qa.world   ;

Commit;
