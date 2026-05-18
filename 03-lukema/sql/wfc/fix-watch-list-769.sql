UPDATE
    T_CMSA_INV_REP_STATUS
SET
    report_dt = to_date('2012-11-15 10:53:38', 'yyyy-mm-dd hh24:Mi;ss')
WHERE
    Investor_Id = 769
AND Periodic_Month = 11
AND Periodic_Year = 2012
AND report_id = 32;


commit;    


SELECT
    *
FROM
    T_CMSA_INV_REP_STATUS
WHERE
    Investor_Id = 769
AND Periodic_Month = 11
AND report_id = 32
AND Periodic_Year = 2012
ORDER BY
    report_id;