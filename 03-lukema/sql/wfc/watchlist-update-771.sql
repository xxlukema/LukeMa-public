UPDATE
    T_CMSA_INV_REP_STATUS
SET
    report_dt = to_date('2012-12-15 10:53:38', 'yyyy-mm-dd hh24:Mi;ss')
WHERE
    Investor_Id = 771
AND Periodic_Month = 12
AND Periodic_Year = 2012
AND report_dt IS NULL;
--AND report_id = 32;
--
COMMIT;

