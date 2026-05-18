--select * from CS_STG_ICE_IA order by COB_DATE desc


/*
select * from CV_LOAD_STATUS
where SOURCE_FEED_NAME like 'ICE%'
and FEED_COB_DATE > to_date('2011-02-15', 'yyyy-mm-dd')
order by FEED_COB_DATE desc
*/


/*
SELECT *
FROM IADATEF
*/


SELECT 
                MFIRM as firm,
                substring(MKEY1,1,1) clazz, 
                substring(MKEY1,2,1) subClazz, 
                substring(MDTL1,1,30) as classSubClassDescription,
                substring(MDTL1,73,1) as segregated,
                MRID1 as keyCode
            FROM IAMASTF_1
            WHERE MRID1 = 'C '
            AND MFIRM || substring(MKEY1,1,1) || substring(MKEY1,2,1) IN
                (SELECT AFIRM || YCLASS || YSUBCL FROM CGMIACTF)
