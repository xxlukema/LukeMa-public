
/*
select * 
from CV_FEED_LOAD_BOX_SETUP
--from CV_FEED_DATA_FILE_SETUP
where SOURCE_FEED_NAME like 'LCH%'
*/

/*
select * from 
CS_FILE_SETUP
where SOURCE_FEED_NAME like 'LCH%'
*/


/*
select count(*) 
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
        (SELECT DECODE (b.dependency || status, 'WAITING', 'DONE', b.dependency || 'PROCESSED', 'DONE', status )
         FROM cv_load_status c 
         WHERE c.feed_cob_date = a.feed_cob_date 
         AND c.source_feed_name = NVL (b.dependency, a.source_feed_name))
      ORDER BY a.update_datetime ASC, feed_cob_date DESC)              
WHERE ROWNUM <= 1;
*/



/*
select *
from CS_STG_LCH_IA
where COB_DATE = to_date('2011-03-25', 'YYYY-MM-DD')
or CLIENT_ACCOUNT_ID = 'MARLCH201114'
order by COB_DATE
desc
*/


/*
select * 
from CS_CH_IA
where COB_DATE = to_date('2011-03-25', 'YYYY-MM-DD')
order by UPDATE_DATETIME desc
*/


/*
select * 
from cv_load_status
where SOURCE_FEED_NAME like 'LCH%JPMSL'
*/


/*
select * from cs_feed_queue 
                  where feed_name = 'LCHEMEAMTMJPMSL110324.DAT' 
                     or (     branch    = 'LCHEMEAMTMJPMSL' 
                          and cob_date  = to_date('110324','RRMMDD') 
                          and feed_type = 'DAT' );
*/



--select * from cs_feed_queue where feed_name like 'LCHEMEA%JPMSL1103%.DAT' 


--select data_file_name,',',nvl(is_flag_file,'YES') from CS_FILE_SETUP where main_or_inter = 'U' and status_code = 'A'


/*
select source_feed_name
						       from CS_FILE_SETUP
						      where data_file_name = 'LCHEMEAMTMJPMSL'
							and main_or_inter = 'M';
*/














































