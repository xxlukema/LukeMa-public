


Select CH_ACCT_NBR, SOURCE_FEED_NAME from CS_CH_IA where CH_ACCT_NBR like '%' || chr(13);
--Select CH_ACCT_NBR, SOURCE_FEED_NAME from CS_CH_MTM where CH_ACCT_NBR like '%' || chr(13);


--Select CH_ACCT_NBR, SOURCE_FEED_NAME, COB_DATE from CS_CH_IA where CH_ACCT_NBR like '%' || chr(13) order by COB_DATE desc;


--Select CH_ACCT_NBR, SOURCE_FEED_NAME, COB_DATE from CS_STG_LCH_IA where SOURCE_FEED_NAME like 'LCH%' order by COB_DATE desc;


--Select CLIENT_ACCOUNT_ID, SOURCE_FEED_NAME, COB_DATE from CS_STG_LCH_IA where SOURCE_FEED_NAME like 'LCH%' order by COB_DATE desc;


--Select SOURCE_FEED_NAME, COB_DATE from CS_STG_LCH_IA where SOURCE_FEED_NAME like 'LCH%' order by COB_DATE desc;


/*
select distinct SOURCE_FEED_NAME 
--from CV_FEED_DATA_FILE_SETUP 
from CS_FILE_SETUP
order by SOURCE_FEED_NAME asc;
*/








