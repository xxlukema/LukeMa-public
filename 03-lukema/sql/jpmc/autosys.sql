
/*
select * from cs_stream_queue
where run_category = 'INT_STMT'
order by cob_date desc;
*/


--select * from cs_feed_queue where FEED_NAME like 'LCH%' order by cob_date desc;

--select * from CS_STG_LCH_IA where source_feed_name like 'LCHEMEA%' order by cob_date desc; 

--select * from cv_load_status where source_feed_name like 'LCHEMEA%' order by feed_cob_date desc;

--select * from cs_ch_feed where SOURCE_FEED_NAME like 'LCH%';
--select * from CS_FILE_SETUP where SOURCE_FEED_NAME like 'LCH%';
--select * from CS_FILE_ID where FILE_ID like 'LCH%';
--select * from CV_FILE_ID where SOURCE_FEED_NAME like 'LCH%';
--select * from CV_FEED_DATA_FILE_SETUP where SOURCE_FEED_NAME like 'LCH%';
--select * from CV_FEED_LOAD_BOX_SETUP where SOURCE_FEED_NAME like 'LCH%';


/*
select * 
from CS_STG_LCH_IA 
where source_FEED_NAME like 'LCHEMEA%JPMSL' 
order by currency, exchange_rate, index_name, client_account_id;
*/

-- Target Tables
--select * from CS_CH_IA where source_FEED_NAME like 'LCHEMEA%JPMSL' order by cob_date desc;
--select * from CS_CH_MTM where source_FEED_NAME like 'LCHEMEA%JPMSL' order by cob_date desc;

-- Staging Tables
--select * from CS_STG_LCH_IA where source_FEED_NAME like 'LCHEMEA%JPMSL' order by COB_DATE desc;
--select * from CS_STG_LCH_MTM where source_FEED_NAME like 'LCHEMEA%JPMSL' order by COB_DATE desc;


--select * from CS_FILE_SETUP where source_FEED_NAME like 'LCHEMEA%JPMSL';
--select * from cv_feed_load_box_setup where source_FEED_NAME like 'LCHEMEA%JPMSL';


--select * from CS_CH_IA  where source_FEED_NAME like 'LCHEMEA%' order by cob_date desc;
select * from CS_CH_MTM where source_FEED_NAME like 'LCHEMEA%' order by cob_date desc;

--delete CS_STG_LCH_IA where source_FEED_NAME like 'LCH%';

/*
delete from CW_USER_GROUP_MEMBER
where SID = 'I047215';
*/

--select * from CS_SEC_USER where ORACLE_ID = 'I047215';






/*
-- CV_FEED_DATA_FILE_SETUP VM
UPDATE OPS$COAST.CV_FEED_DATA_FILE_SETUP
SET LOAD_TABLE_TYPE = 'MULTIPLE_FEEDS'
WHERE SOURCE_FEED_NAME LIKE 'LCH%';
*/


/*
select LOAD_TABLE_NAME, SOURCE_FEED_NAME, LOAD_TABLE_TYPE,
       NVL(LOAD_TABLE_TYPE,  'SINGLE_FEED'),
       NVL(FIELD_DELIMITER,  'TAB'),
       NVL(HEADER_FOOTER,    'NONE'),
       NVL(IS_ADD_COB_DATE,  'NO'),
       NVL(IS_ADD_FEED_NAME, 'NO')
from cv_feed_data_file_setup
where data_file_name like 'LCH%';
*/


/*
UPDATE OPS$COAST.CS_CH_FEED
SET DOWNLOAD_LCH_FLAG = 'Y'
WHERE SOURCE_FEED_NAME like 'LCH%';
*/


/*
select * from CS_INTSTMT_STATUS
where STATEMENT_ID = 383318
or STATEMENT_ID = 512654;
*/


/*
select * from CS_INTSTMT_STATUS
where ACCOUNT_NUMBER = '33333301A';
*/


--select * from CS_INTSTMT_BALANCE where compound_ind = 'Y';
--select * from CS_INTSTMT_RAW_DATA where ACCOUNT_NUMBER = '33333301A';


/*
select * 
from CS_INTSTMT_RES_MOVE_BO
where STATEMENT_ID = 548161;
*/


/*
select * 
from CS_INTSTMT_RES_MOVE_BO
where STATEMENT_ID = 383318
or STATEMENT_ID = 512654;
*/


/*
select * from CS_INTSTMT_BALANCE
where STATEMENT_ID = 383318
or STATEMENT_ID = 512654;
*/


/*
select * from CS_INTSTMT_RESULT
where STATEMENT_ID = 383318
or STATEMENT_ID = 512654;
*/


/*
update CS_INTSTMT_RESULT
set ASSET_DLY_INT = 1234,
    LIAB_DLY_INT = 34567
where STATEMENT_ID = 383318;
*/


/*
update CS_INTSTMT_BALANCE
set ASSET_DLY_INT = 1234,
    LIAB_DLY_INT = 34567
where STATEMENT_ID = 383318;
*/


/*
update CS_INTSTMT_RESULT
set ASSET_DLY_INT = 1234,
    LIAB_DLY_INT = 34567,
    COMPOUND_IND = 'Y'
where STATEMENT_ID = 512654;
*/


/*
update CS_INTSTMT_BALANCE
set ASSET_DLY_INT = 1234,
    LIAB_DLY_INT = 34567,
    COMPOUND_IND = 'Y'
where STATEMENT_ID = 512654;
*/


/*
select * 
from CS_INTSTMT_BALANCE
where LIAB_DLY_INT > 0
or ASSET_DLY_INT > 0
*/


/*
select * 
from cs_file_id
where FILE_ID like 'LCH%';
*/


/*
select * 
from CS_CH_IA
WHERE SOURCE_FEED_NAME like 'LCH%'
order by COB_DATE desc
*/


/*
select * 
from CS_CH_MTM
order by COB_DATE desc
*/

--select * from cs_ch_feed


/*
UPDATE OPS$COAST.CS_CH_FEED
SET LCH_SITE = '194.62.172.34:444/Reporting', 
LCH_USER = 'STSTJPLCLEAR7',
LCH_PWD = 'RCCJPLMO1',
LEGAL_ENTITY = 'JPMSL'
WHERE SOURCE_FEED_NAME = 'LCHEMEAIA-JPMSL'
OR SOURCE_FEED_NAME = 'LCHEMEAMTM-JPMSL';
*/


/*
-- UAT
UPDATE OPS$COAST.CS_CH_FEED
SET DOWNLOAD_LCH_FLAG = 'Y'
WHERE SOURCE_FEED_NAME = 'LCH%';
*/

/*
select *
from CS_FILE_SETUP
where SOURCE_FEED_NAME like 'LCH%'
*/



--select * from cs_feed_queue where FEED_NAME like 'LCH%';





