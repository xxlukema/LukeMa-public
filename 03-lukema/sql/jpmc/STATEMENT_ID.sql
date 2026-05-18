--select * from CS_CH_IA where source_FEED_NAME like 'LCHEMEA%JPMSL' order by cob_date desc;


/*
select
    COB_DATE,
    CH_ACCT_NBR,
    CH_IA_AMT,
    CH_IA_CCY,
    SOURCE_FEED_NAME,
    FEED_ID,
    UPDATE_DATETIME
from CS_CH_IA where source_FEED_NAME like 'LCHEMEA%JPMSL' order by cob_date desc;
*/


--select * from CV_FEED_LOAD_BOX_SETUP where SOURCE_FEED_NAME like 'LCHEMEA%JPMSL';

--Select CH_ACCT_NBR, SOURCE_FEED_NAME from CS_CH_IA where CH_ACCT_NBR like '%' || chr(13);

/*
select CS_INTSTMT_CONTACT.STATEMENT_ID, CS_INTSTMT_STATUS.STATEMENT_ID, CS_INTSTMT_RESULT.*
from CS_INTSTMT_RESULT, CS_INTSTMT_CONTACT, CS_INTSTMT_STATUS
where CS_INTSTMT_RESULT.STATEMENT_ID = CS_INTSTMT_STATUS.STATEMENT_ID
and CS_INTSTMT_STATUS.STATEMENT_ID = CS_INTSTMT_CONTACT.STATEMENT_ID;
*/


/*
select CS_INTSTMT_CONTACT.STATEMENT_ID, CS_INTSTMT_CONTACT.INTSTMT_CONTACT_ID, CS_INTSTMT_CONTACT.UPDATE_DATETIME, CS_INTSTMT_STATUS.*
from CS_INTSTMT_CONTACT, CS_INTSTMT_STATUS
where CS_INTSTMT_STATUS.STATEMENT_ID = CS_INTSTMT_CONTACT.STATEMENT_ID
order by CS_INTSTMT_CONTACT.STATEMENT_ID asc;
*/


/*
select * from CS_INTSTMT_CONTACT
order by STATEMENT_ID asc;
*/

--select * from OPS$COAST.CS_INTSTMT_STATUS where STATEMENT_ID = 557623;


/*
select * from ops$coast.cs_intstmt_move
--where statement_id=557825 
order by UPDATE_DATETIME desc;
*/

--select * from CS_INTSTMT_STATUS where statement_id=557825 

/*
select sum(cnt) from (
 select count(1) cnt from cv_file_id 
 where source_feed_name='LCHEMEAIAJPMSL' and rownum=1 
 union 
 select count(1) cnt from cv_file_id where source_feed_name like 'LCHEMEAIAJPMSL%');
*/

--select * from CV_FEED_LOAD_BOX_SETUP where source_feed_name like 'LCHEMEAIAJPMSL%'

/*
select LOAD_TABLE_NAME, SOURCE_FEED_NAME,
       NVL(LOAD_TABLE_TYPE,  'SINGLE_FEED'),
       NVL(FIELD_DELIMITER,  'TAB'),
       NVL(HEADER_FOOTER,    'NONE'),
       NVL(IS_ADD_COB_DATE,  'NO'),
       NVL(IS_ADD_FEED_NAME, 'NO')
from cv_feed_data_file_setup
where data_file_name = 'LCHEMEAIAJPMSL';
*/

select cv_get_table_columns_to_load('CS_STG_LCH_MTM','DD/MM/YYYY') from dual;







