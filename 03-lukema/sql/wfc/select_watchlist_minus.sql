SELECT
    LOAN_ID,
    EFFECTIVE_DT
FROM
    T_CMSA_WATCHLIST
WHERE
    EFFECTIVE_DT = to_date('2012-03-21', 'yyyy-mm-dd')
minus
select 
   --distinct 
   LOAN_ID,
   EFFECTIVE_DT
from (
        SELECT s.loan_id, to_date('2012-03-21', 'yyyy-mm-dd') as EFFECTIVE_DT
                         FROM T_CMSA_SPECIAL_SERVICING s, T_CMSA_POOL pl, T_CMSA_INVESTOR i
                        WHERE s.loan_id = pl.loan_id
                          AND s.loan_id NOT IN (SELECT w.loan_id
                                                  FROM T_CMSA_WATCHLIST w
                                                 WHERE w.release_dt IS NULL)
                          AND s.return_dt IS NULL
                          and i.investor_id = pl.investor_id
                          and i.sub_srvr_off_flag = 'N'
        union                  
        SELECT s.loan_id, to_date('2012-03-21', 'yyyy-mm-dd') as EFFECTIVE_DT
                         FROM T_CMSA_SPECIAL_SERVICING s,
                              T_CMSA_POOL pl,
                              T_CMSA_LOAN LN, T_CMSA_INVESTOR i
                        WHERE s.loan_id = pl.loan_id
                          AND pl.loan_id = LN.loan_id
                          AND s.loan_id NOT IN (SELECT w.loan_id
                                                  FROM T_CMSA_WATCHLIST w
                                                 WHERE w.release_dt IS NULL)
                          AND s.return_dt IS NULL
                          AND pl.investor_id = i.investor_id
                          and i.sub_srvr_off_flag != 'N'
                          AND LN.sub_servicer_cd NOT BETWEEN 1 AND 999
)
    