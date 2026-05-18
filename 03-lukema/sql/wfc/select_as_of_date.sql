SELECT
    Report_Deter_Dt  AS v_AsOfDate,
    Report_Distri_Dt AS v_dDistriDt,
    Trustee_Remit_Dt AS v_dRemitDt
FROM
    T_CMSA_DISTRIBUTION_CALENDAR
WHERE
    Investor_Id = 571
AND Reporting_Month = 4
AND Reporting_Year = 2012 ;