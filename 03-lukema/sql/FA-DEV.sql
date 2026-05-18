select * from case_field where id_case = 'AP080003XX' and id_subtype_case like 'A1%' ;

select * from casepart where ID_UNIQUE_CPRT like '%56798' order by isn desc;

select * from casepart where isn >= 377712;

select * from casepart_mu where ISN >= 377706;

select * from participant where STREET_ADR_ID = '328586';

----
---select * from c_part where part_id = '377514';
---
select * from casepart where ID_UNIQUE_CPRT like '%83698';
select * from casepart where ISN like '%83758';

select * from CASEPART_GROUP_CONTACT_CPRT where isn like '%377671';
select * from CASEPART_MU where isn like '%377671';

rollback;

commit;

delete from casepart where NME_PART_CPRT is null;

ALTER TABLE casepart ADD CONSTRAINT cstrt_NME_PART_CPRT CHECK(NME_PART_CPRT IS NOT NULL);
ALTER TABLE casepart ADD CONSTRAINT cstrt_ID_PART_CPRT CHECK(ID_PART_CPRT IS NOT NULL);

select * from case_field where id_case like 'AP%XX' and id_subtype_case like 'A1%' order by id_case desc ;


select count(*) from casepart where NME_PART_CPRT is null;

select '###' || ID_PART_CPRT || '===' from casepart where trim(ID_PART_CPRT) is null;

select length(trim(ISO_31661_COUNTRY_NAME)) from cmx_ors.c_lu_country order by 1 desc;

select * from case_field where isn = '0000056719';

select CASE_FIELD.ISN,ID_CASE,PCT_COUNTER_GUAR_CASE,CDE_GUAR_COVER_BY_CASE,CDE_TERM_CASE,PCT_SMALL_BUS_CASE,CDE_SALE_TYPE_CASE,CDE_COUNTER_GUAR_TYPE_CASE,CDE_ADDITIONALITY_CASE,NUM_MGA_CASE,CDE_COFIN_TYPE_CASE,AMT_COUNTER_GTEE_CASE,CDE_PGM_TYPE_CASE,CDE_LEAD_FOLLOW_CASE,PCT_DIRECT_SM_BUS_CASE,CDE_ADDITIONALITY_SEC1_CASE,CDE_ADDITIONALITY_SEC2_CASE,CDE_ASU_CASE  from CASE_FIELD where ISN='56719';

SELECT CASE_FIELD.ISN,
  ID_CASE,
  PCT_SMALL_BUS_CASE,
  PCT_DIRECT_SM_BUS_CASE
FROM CASE_FIELD
WHERE ISN='56719';

select EMPL_SECURITY.ISN,ID_EMPL,CDE_USER_GP_EMPL,CDE_USER_GP_LGA_EMPL,CDE_USER_GP_AAA_EMPL  from EMPL_SECURITY where (EMPL_SECURITY.ID_EMPL ='TURNERT') order by (0+ISN);

select * from case_field where id_subtype_case like 'A1%' order by isn desc  ;

select * from casepart where ID_UNIQUE_CPRT like '%83795';
--
select * from case_field where /*id_case = 'AP080003XX'*/ ID_UNIQUE_CASE='0000083800' and id_subtype_case like 'A1%' ;
--