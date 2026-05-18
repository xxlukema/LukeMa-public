CREATE OR REPLACE VIEW OPS$COAST.CS_CONTACT_STATUS
(
    CONTACT_RECORD_ID,
    DCOUNT,
    ACOUNT,
    PCOUNT,
    RCOUNT,
    CON_ACOUNT,
    AGR_ACOUNT,
    CON_PCOUNT,
    AGR_PCOUNT,
    CON_RCOUNT,
    AGR_RCOUNT,
    CON_ZCOUNT,
    AGR_ZCOUNT,
    COMB_CONTACT
)
AS
select  b.contact_record_id,sum(decode(b.status_code,'D',rows_cnt,0)) Dcount,
	sum(decode(b.status_code,'A',rows_cnt,0)) ACOUNT,
	sum(decode(b.status_code,'P',rows_cnt,0)) PCOUNT,
	sum(decode(b.status_code,'R',rows_cnt,0)) RCOUNT,
	sum(decode(b.table_id,'CONTACT',decode(b.status_code,'A',rows_cnt,0),0)) CON_ACOUNT,
	sum(decode(b.table_id,'AGREE',decode(b.status_code,'A',rows_cnt,0),0)) AGR_ACOUNT,
	sum(decode(b.table_id,'CONTACT',decode(b.status_code,'P',rows_cnt,0),0)) CON_PCOUNT,
	sum(decode(b.table_id,'AGREE',decode(b.status_code,'P',rows_cnt,0),0)) AGR_PCOUNT,
	sum(decode(b.table_id,'CONTACT',decode(b.status_code,'R',rows_cnt,0),0)) CON_RCOUNT,
	sum(decode(b.table_id,'AGREE',decode(b.status_code,'R',rows_cnt,0),0)) AGR_RCOUNT,
	sum(decode(b.table_id,'CONTACT',decode(b.status_code,'A',0,'P',0,'R',0,'D',0,rows_cnt),0)) CON_ZCOUNT,
	sum(decode(b.table_id,'AGREE',decode(b.status_code,'A',0,'P',0,'R',0,'D',0,rows_cnt),0)) AGR_ZCOUNT,
    sum(DECODE(b.table_id, 'COMBINED', DECODE(b.status_code, 'A', rows_cnt, 0), 0)) COMB_CONTACT
	 from
	 (  select a.contact_record_id,a.status_code,a.table_id, count(*) rows_cnt from
( select contact_record_id,status_code,'CONTACT' table_id from
      cs_contact_info where status_code != 'D'
	  union all
	select contact_record_id,status_code,'AGREE' table_id from
	   cs_contact_agreement where status_code != 'D'  
       union all 
      select contact_record_id,
         status_code,
         'COMBINED' table_id
      from 	 MO_COMBINED_STMT_CONTACT 
      where status_code != 'D') a
	   group by a.contact_record_id,a.status_code,a.table_id ) b
	    group by b.contact_record_id