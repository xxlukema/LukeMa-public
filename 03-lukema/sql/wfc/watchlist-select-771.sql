SELECT
    RS.*
FROM
    T_CMSA_INV_REP_STATUS RS,
    T_CMSA_INV_REPORT IR,
    T_CMSA_REPORT R
WHERE
    RS.Investor_Id = IR.Investor_Id
AND RS.Report_Id = IR.Report_ID
AND IR.Report_Id = R.Report_ID
--AND R.Report_Cd = 'LPER'
AND IR.Investor_Id = 771
AND RS.Periodic_Month = 12
AND RS.Periodic_Year = 2012;