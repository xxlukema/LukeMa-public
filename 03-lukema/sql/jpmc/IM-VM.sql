

/*
--select source_feed_name, sql_script, staging_table
select *
from cv_feed_load_box_setup
where (source_feed_name like 'LCH%IA%' or source_feed_name like 'LCH%MTM%')
and job_name = 'CV_ImportCH';
order by source_feed_name
*/


/*
select * 
from CS_STG_LCH_IA
where source_feed_name like 'LCH%IA%'
or source_feed_name like 'LCH%MTM%'
order by source_feed_name
*/


/*
set feedback off underline off pages 0 lines 999 heading off
select cv_feedidseq.nextval from dual;
*/


/*
select * 
from CS_CH_FEED
where source_feed_name like 'LCH%IA%'
or source_feed_name like 'LCH%MTM%'
order by source_feed_name
*/

/*
SELECT source_feed_name, feed_cob_date, cv_feed_id
FROM cv_load_status
where source_feed_name like 'LCH%IA%'
or source_feed_name like 'LCH%MTM%'
order by source_feed_name
*/


/*
--FEED INFO IM VM
SELECT *
FROM cs_ch_feed
where source_feed_name like 'LCH%IA%'
or source_feed_name like 'LCH%MTM%'
order by source_feed_name
*/


--FEED INFO
SELECT *
FROM cs_ch_feed
order by source_feed_name


/*
SELECT *
FROM cs_clearing_house
order by ch_id
*/


/*
select * 
from CS_CH_IA
*/


/*
select * 
from CS_CH_MTM
*/







