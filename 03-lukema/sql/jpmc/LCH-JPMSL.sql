

/*
-- Setup
select * from CS_CH_FEED where SOURCE_FEED_NAME like 'LCH%';
select * from CS_FILE_SETUP where SOURCE_FEED_NAME like 'LCH%';
select * from CS_FILE_ID where FILE_ID like 'LCH%';
select * from CV_FILE_ID where SOURCE_FEED_NAME like 'LCH%';
select * from CV_FEED_DATA_FILE_SETUP where SOURCE_FEED_NAME like 'LCH%';
select * from CV_FEED_LOAD_BOX_SETUP where SOURCE_FEED_NAME like 'LCH%';
*/


/*
-- CV_FEED_DATA_FILE_SETUP VM
UPDATE OPS$COAST.CV_FEED_DATA_FILE_SETUP
SET LOAD_TABLE_TYPE = 'MULTIPLE_FEEDS'
WHERE SOURCE_FEED_NAME LIKE 'LCH%';
*/


/*
-- Monitor
delete from CS_FEED_QUEUE where FEED_NAME like 'LCHEMEA%JPMSL';
delete from CV_LOAD_STATUS where source_feed_name like 'LCHEMEA%JPMSL';

-- Target Tables
delete from CS_CH_IA where source_FEED_NAME like 'LCHEMEA%JPMSL';
delete from CS_CH_MTM where source_FEED_NAME like 'LCHEMEA%JPMSL';

-- Staging Tables
delete from CS_STG_LCH_IA where source_FEED_NAME like 'LCHEMEA%JPMSL';
delete from CS_STG_LCH_MTM where source_FEED_NAME like 'LCHEMEA%JPMSL';

commit;
*/



-- Monitor
select * from CS_FEED_QUEUE where FEED_NAME like 'LCHEMEA%JPMSL%' order by cob_date desc;
select * from CV_LOAD_STATUS where source_feed_name like 'LCHEMEA%JPMSL' order by feed_cob_date desc;

-- Target Tables
select * from CS_CH_IA where source_FEED_NAME like 'LCHEMEA%JPMSL' and rownum < 30 order by cob_date desc;
select * from CS_CH_MTM where source_feed_name like 'LCHEMEA%JPMSL' and rownum < 30 order by cob_date desc;

-- Staging Tables
select * from CS_STG_LCH_IA where source_FEED_NAME like 'LCHEMEA%JPMSL' and rownum < 30 order by COB_DATE desc;
select * from CS_STG_LCH_MTM where source_feed_name like 'LCHEMEA%JPMSL' and rownum < 30 order by COB_DATE desc;



