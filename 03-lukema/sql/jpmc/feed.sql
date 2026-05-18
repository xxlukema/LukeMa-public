

--select * from CS_file_id where alarm_flag != 0 order by exp_time;

--select LOAD_TYPE, FILE_ID, COUNTRY from cs_file_id 
--where to_char(effective_date,'YYYYMMDD') <= '$CC$COB' 
--and PRIMARY_DATABASE = 'COAST';

/*
    select WATCHER from cs_watcher_status  union all 
    select WATCHER from cv_watcher_status  union all 
    select WATCHER from cw_watcher_status  union all 
    select WATCHER from pfc_watcher_status
*/

/*
SELECT SOURCE_FEED_NAME, DOWNLOAD_SCRIPT_NAME, DOWNLOAD_TIME
FROM cs_ch_feed where status = 'A';
*/

/*
SELECT SOURCE_FEED_NAME, DOWNLOAD_SCRIPT_NAME, DOWNLOAD_TIME
FROM cs_ch_feed where status = 'A';
*/


--select distinct exp_time, time_diff from CS_file_id where alarm_flag != 0 order by exp_time;

/*
select *
from CV_LOAD_STATUS 
where primary_database <> 'ODS' 
and status = 'RUNNING';
*/


/*
SELECT source_feed_name, feed_cob_date, cv_feed_id, status, update_datetime 
              FROM (SELECT   a.source_feed_name, TO_CHAR (feed_cob_date, 'RRMMDD') feed_cob_date, cv_feed_id, 
                             a.status, a.update_datetime, b.dependency 
                        FROM cv_load_status a, cv_file_id b 
                       WHERE status = 'WAITING' 
                         AND feed_cob_date >= SYSDATE - 45 
                         AND a.source_feed_name = b.source_feed_name(+) 
                         AND 'DONE' = 
                                (SELECT DECODE (b.dependency || status, 
                                                'WAITING', 'DONE', 
                                                b.dependency || 'PROCESSED', 'DONE', 
                                                status 
                                               ) 
                                   FROM cv_load_status c 
                                  WHERE c.feed_cob_date = a.feed_cob_date 
                                    AND c.source_feed_name = NVL (b.dependency, a.source_feed_name)) 
                      ORDER BY a.update_datetime ASC, feed_cob_date DESC
                    ) 
             WHERE ROWNUM <= 1;
*/

--select status, count(*) from CV_LOAD_STATUS group by status

--select * from OPS$COAST.CS_CH_FEED where DOWNLOAD_TIME = '21:30';


/*
UPDATE OPS$COAST.CS_CH_FEED
SET DOWNLOAD_TIME = '21:30:00'
WHERE DOWNLOAD_TIME = '21:30';
*/


--select * from OPS$COAST.cs_feed_queue where feed_name like 'LCH%';


select distinct FILE_ID from CS_CONTROL;




/*
SELECT c.SOURCE_FEED_NAME, b.dependency || c.status, b.dependency, DECODE (b.dependency || c.status, b.dependency ||'WAITING', 'DONE', 
                                                                                           b.dependency || 'PROCESSED', 'DONE2', 
                                                                                           c.status) 
FROM cv_load_status c, cv_file_id b  
WHERE ( c.source_feed_name = b.dependency or c.source_feed_name = b.source_feed_name)
and b.dependency is not null
--and c.SOURCE_FEED_NAME like '%LD'
*/


/*
select * from CV_FILE_ID
where SOURCE_FEED_NAME like 'LCH%'
*/


















